package com.garage.controller;

import com.garage.entity.Offroad;
import com.garage.service.OffroadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offroad")
public class OffroadController {

    private final OffroadService offService;

    public OffroadController(OffroadService offService) {
        this.offService = offService;
    }

    @PutMapping("/clear/{id}")
    public ResponseEntity<Offroad> clearOffroad(@PathVariable Long id) {
        return ResponseEntity.ok(offService.clear(id));
    }
}
