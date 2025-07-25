package com.garage.repository;

import com.garage.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findByPartNumber(String partNumber);
}