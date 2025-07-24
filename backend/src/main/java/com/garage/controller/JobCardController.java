package com.garage.controller;

import com.garage.dto.ApprovalRequest;
import com.garage.dto.CreateJobCardRequest;
import com.garage.dto.UpdateStatusRequest;
import com.garage.entity.JobCard;
import com.garage.entity.JobCardStatus;
import com.garage.service.JobCardService;
import com.garage.dto.JobCardResponse;
import com.garage.dto.JobCardDashboardResponse;
import com.garage.entity.JobCardHistory;
import com.garage.entity.User;

import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobcards")
public class JobCardController {

    private final JobCardService service;

    public JobCardController(JobCardService service) {
        this.service = service;
    }

    // 1) Create
    @PostMapping
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<JobCardResponse> create(
            @Valid @RequestBody CreateJobCardRequest req,
            UriComponentsBuilder uriBuilder
    ) {
        JobCard created = service.create(new JobCard(
                req.getDriverId(),
                req.getVehicleId(),
                req.getDescription()
        ));

        URI location = uriBuilder
                .path("/api/jobcards/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(new JobCardResponse(created));
    }

    // 2) Get current driver's job cards
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<List<JobCardResponse>> listMyJobCards(
            @AuthenticationPrincipal User user
    ) {
        List<JobCard> cards = service.findByDriver(user.getId());
        List<JobCardResponse> resp = cards.stream().map(JobCardResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    // 3) Get all job cards (Admin)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobCardResponse>> listAll() {
        List<JobCardResponse> resp = service.findAll().stream()
                .map(JobCardResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    // 4) Get by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','STAFF','ADMIN')")
    public ResponseEntity<JobCardResponse> getById(@PathVariable Long id) {
        JobCard card = service.getById(id);
        return ResponseEntity.ok(new JobCardResponse(card));
    }

    // 5) Approve
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<JobCardResponse> approve(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalRequest req
    ) {
        JobCard updated = service.approve(id, req);
        return ResponseEntity.ok(new JobCardResponse(updated));
    }

    // 6) Delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 7) Update status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<JobCardResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest req
    ) {
        JobCard updated = service.updateStatus(id, JobCardStatus.valueOf(req.getStatus().toUpperCase()));
        return ResponseEntity.ok(new JobCardResponse(updated));
    }

    // 8) Filter by status
    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<JobCardResponse>> listByStatus(@RequestParam String status) {
        JobCardStatus statusEnum = JobCardStatus.valueOf(status.toUpperCase());
        List<JobCardResponse> resp = service.findByStatus(statusEnum)
                .stream().map(JobCardResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    // 9) Filter by vehicle
    @GetMapping("/vehicle/{vehicleId}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<JobCardResponse>> listByVehicle(@PathVariable String vehicleId) {
        List<JobCard> cards = service.findByVehicle(vehicleId);
        List<JobCardResponse> resp = cards.stream().map(JobCardResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    // 10) Admin list by driver
    @GetMapping("/driver/{driverId}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<JobCardResponse>> listByDriver(@PathVariable Long driverId) {
        List<JobCard> cards = service.findByDriver(driverId);
        List<JobCardResponse> resp = cards.stream().map(JobCardResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    // 11) Check if vehicle has an open job card
    @GetMapping("/open/{vehicleId}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<Boolean> hasOpenJobCard(@PathVariable String vehicleId) {
        List<JobCard> cards = service.findByVehicle(vehicleId);
        boolean hasOpen = cards.stream().anyMatch(c -> c.getStatus() != JobCardStatus.CLOSED);
        return ResponseEntity.ok(hasOpen);
    }

    // 12) Paginated job card listing
   @GetMapping("/page/filter")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<Page<JobCardResponse>> listByStatusPaginated(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        JobCardStatus statusEnum = JobCardStatus.valueOf(status.toUpperCase());
        Page<JobCard> paged = repo.findByStatus(statusEnum, PageRequest.of(
                page,
                size,
                sortDirection.equalsIgnoreCase("desc") ?
                        Sort.by(sortBy).descending() :
                        Sort.by(sortBy).ascending()
        ));
        Page<JobCardResponse> mapped = paged.map(JobCardResponse::new);
        return ResponseEntity.ok(mapped);
    }

    // 13) Get job cards assigned to a mechanic
    @PutMapping("/{id}/assign-mechanic/{mechanicId}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<JobCardResponse> assignMechanic(
            @PathVariable Long id,
            @PathVariable Long mechanicId
    ) {
        JobCard updated = service.assignMechanic(id, mechanicId);
        return ResponseEntity.ok(new JobCardResponse(updated));
    }

    // 14) List all mechanics (for assignment)
    @GetMapping("/mechanics")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<List<User>> listMechanics() {
        List<User> mechanics = userRepo.findByRole(Role.ROLE_MECHANIC);
        return ResponseEntity.ok(mechanics);
    }

    // 15) List job cards assigned to the current mechanic
    @GetMapping("/my-assigned")
    @PreAuthorize("hasRole('MECHANIC')")
    public ResponseEntity<List<JobCardResponse>> listMyAssigned(
            @AuthenticationPrincipal User user
    ) {
        List<JobCard> cards = service.findByMechanic(user.getId());
        List<JobCardResponse> resp = cards.stream()
            .map(JobCardResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }


    // 16) Get dashboard summary
    @GetMapping("/dashboard-summary")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<JobCardDashboardResponse> getDashboardSummary() {
        return ResponseEntity.ok(service.getDashboardSummary());
    }

    // 17) Get job card history
    @GetMapping("/{id}/history")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','DRIVER','MECHANIC')")
    public ResponseEntity<List<JobCardHistory>> getJobCardHistory(@PathVariable Long id) {
        List<JobCardHistory> history = historyRepo.findByJobCardId(id);
        return ResponseEntity.ok(history);
    }




}
