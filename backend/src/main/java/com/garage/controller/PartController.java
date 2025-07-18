package com.garage.controller;

import com.garage.entity.Part;
import com.garage.service.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    private final PartService service;

    public PartController(PartService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Part> create(@RequestBody Part part) {
        Part created = service.create(part);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Part>> list() {
        return ResponseEntity.ok(service.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Part> update(
            @PathVariable Long id,
            @RequestBody Part part
    ) {
        Part updated = service.update(id, part);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // DTO for consumption/restock
    public static class PartUsageRequest {
        @NotBlank
        private String partNumber;
        @Min(1)
        private int quantity;

        public String getPartNumber() {
            return partNumber;
        }

        public void setPartNumber(String partNumber) {
            this.partNumber = partNumber;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // Check availability
    @GetMapping("/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @RequestParam String partNumber,
            @RequestParam int quantity
    ) {
        boolean ok = service.isAvailable(partNumber, quantity);
        Map<String, Object> res = Map.of(
            "partNumber", partNumber,
            "requested", quantity,
            "available", ok
        );
        return ResponseEntity.ok(res);
    }

    // Consume stock
    @PostMapping("/consume")
    public ResponseEntity<Void> consumePart(
            @Valid @RequestBody PartUsageRequest req
    ) {
        service.consume(req.getPartNumber(), req.getQuantity());
        return ResponseEntity.noContent().build();
    }

    // Restock parts
    @PostMapping("/restock")
    public ResponseEntity<Part> restockPart(
            @Valid @RequestBody PartUsageRequest req
    ) {
        Part updated = service.restock(req.getPartNumber(), req.getQuantity());
        return ResponseEntity.ok(updated);
    }
}
