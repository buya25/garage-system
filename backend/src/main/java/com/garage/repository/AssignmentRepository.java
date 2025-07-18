package com.garage.repository;

import com.garage.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByJobCard_DriverId(Long driverId);
    List<Assignment> findByMechanicId(Long mechanicId);
}