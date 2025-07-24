package com.garage.repository;

import com.garage.entity.Offroad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OffroadRepository extends JpaRepository<Offroad, Long> {
    Optional<Offroad> findFirstByJobCardIdAndStatus(Long jobCardId, String status);
}
