package com.garage.service;

import com.garage.entity.Offroad;
import com.garage.repository.OffroadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class OffroadService {

    private final OffroadRepository offRepo;

    public OffroadService(OffroadRepository offRepo) {
        this.offRepo = offRepo;
    }

    @Transactional
    public Offroad clear(Long offroadId) {
        Offroad off = offRepo.findById(offroadId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offroad record not found"));

        if ("CLEARED".equals(off.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already cleared");
        }

        off.setStatus("CLEARED");
        off.setClearedAt(LocalDateTime.now());
        return offRepo.save(off);
    }
}
