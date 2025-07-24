package com.garage.repository;

import com.garage.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    // Find assignments by job card driver ID
    List<Assignment> findByJobCard_DriverId(Long driverId);
    // Find assignments by job card vehicle ID
    List<Assignment> findByMechanicId(Long mechanicId);
    // Find assignments by job card status
    List<Assignment> findByJobCard_VehicleId(Long vehicleId);
    // Find assignments by job card status
    List<Assignment> findByJobCard_Status(String status);
    // Add more custom query methods as needed
}