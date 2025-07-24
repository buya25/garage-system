package com.garage.controller;

import com.garage.dto.AssignmentRequest;
import com.garage.entity.Assignment;
import com.garage.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.garage.dto.StatusUpdateRequest;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    // Assign an approved job card to a mechanic
    @PostMapping
    public ResponseEntity<Assignment> assign(@Valid @RequestBody AssignmentRequest req) {
        Assignment result = service.assignToMechanic(req);
        return ResponseEntity.ok(result);
    }

    // List assignments by mechanic or driver
    @GetMapping
    public ResponseEntity<List<Assignment>> list(
        @RequestParam(required = false) Long mechanicId,
        @RequestParam(required = false) Long driverId
    ) {
        List<Assignment> result;
        if (mechanicId != null) {
            result = service.listByMechanic(mechanicId);
        } else if (driverId != null) {
            result = service.listByDriver(driverId);
        } else {
            result = List.of();
        }
        return ResponseEntity.ok(result);
    }

    // Update assignment status
    @PutMapping("/{id}/status")
    public ResponseEntity<Assignment> updateStatus(
        @PathVariable("id") Long assignmentId,
        @Valid @RequestBody StatusUpdateRequest req
    ) {
        Assignment result = service.updateStatus(assignmentId, req);
        return ResponseEntity.ok(result);
    }

    // Get assignment details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getById(@PathVariable("id") Long assignmentId) {
        Assignment result = service.getById(assignmentId);
        return ResponseEntity.ok(result);
    }
    // List assignments by mechanic
    @GetMapping("/mechanic/{mechanicId}")
    public ResponseEntity<List<Assignment>> listByMechanic(@PathVariable("mechanicId") Long mechanicId) {
        List<Assignment> result = service.listByMechanic(mechanicId);
        return ResponseEntity.ok(result);
    }
    // List assignments by driver
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Assignment>> listByDriver(@PathVariable("driverId") Long driverId) {
        List<Assignment> result = service.listByDriver(driverId);
        return ResponseEntity.ok(result);
    }
    
    // Delete assignment by ID (optional, if needed)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long assignmentId) {
        service.delete(assignmentId);
        return ResponseEntity.noContent().build();
    }
    // List all assignments (Admin only)
    @GetMapping("/all")
    public ResponseEntity<List<Assignment>> listAll() {
        List<Assignment> result = service.findAll();
        return ResponseEntity.ok(result);
    }

}