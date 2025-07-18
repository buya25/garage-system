package com.garage.controller;

import com.garage.entity.JobCard;
import com.garage.dto.CreateJobCardRequest;
import com.garage.dto.ApprovalRequest;
import com.garage.service.JobCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/jobcards")
public class JobCardController {

    private final JobCardService service;

    public JobCardController(JobCardService service) {
        this.service = service;
    }

    // Create a new job card
    @PostMapping
    public ResponseEntity<JobCard> create(@Valid @RequestBody CreateJobCardRequest req) {
       JobCard card = new JobCard(
            req.getDriverId(),
            req.getVehicleId(),
            req.getDescription()
        );
        JobCard created = service.create(card);
        return ResponseEntity.ok(created);
    }

    // List all job cards for a driver
    @GetMapping
    public ResponseEntity<List<JobCard>> listByDriver(@RequestParam Long driverId) {
        List<JobCard> cards = service.findByDriver(driverId);
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<JobCard> approve(
        @PathVariable Long id,
        @Valid @RequestBody ApprovalRequest request
    ) {
        JobCard updated = service.approve(id, request);
        return ResponseEntity.ok(updated);
    }
}
