package com.garage.controller;

import com.garage.entity.JobCard;
import com.garage.service.JobCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<JobCard> create(@RequestBody JobCard card) {
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
            @RequestBody ApprovalRequest request
    ) {
        JobCard updated = service.approve(id, request.getRequiresParts());
        return ResponseEntity.ok(updated);
    }
}
