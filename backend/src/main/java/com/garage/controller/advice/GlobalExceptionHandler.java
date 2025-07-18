package com.garage.controller.advice;

import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import com.garage.service.PartService;
import com.garage.repository.JobCardRepository;
import com.garage.entity.JobCard;
import com.garage.dto.PartUsageRequest;
import com.garage.dto.ApprovalRequest;
import jakarta.validation.Valid;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final PartService partService;
    private final JobCardRepository repo;

    public GlobalExceptionHandler(JobCardRepository repo, PartService partService) {
        this.repo = repo;
        this.partService = partService;
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    // Handle not found
    @ExceptionHandler({RuntimeException.class, EntityNotFoundException.class})
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    // Fallback for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("An unexpected error occurred");
    }

    // Handle illegal state (e.g., assignment before approval)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    public ResponseEntity<JobCard> approve(
        @PathVariable Long id,
        @Valid @RequestBody ApprovalRequest request
    ) {
        JobCard card = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("JobCard not found for id: " + id));
        card.setRequiresParts(request.getRequiresParts());

        if (!request.getRequiresParts()) {
            card.setStatus("APPROVED");
        } else {
            boolean allAvailable = request.getParts().stream()
                .allMatch(pu -> partService.isAvailable(pu.getPartNumber(), pu.getQuantity()));
            if (allAvailable) {
                request.getParts().forEach(pu -> partService.consume(pu.getPartNumber(), pu.getQuantity()));
                card.setApproved(true);
                card.setStatus("APPROVED");
            } else {
                card.setStatus("PENDING_PARTS");
            }
        }
        return ResponseEntity.ok(repo.save(card));
    }
}