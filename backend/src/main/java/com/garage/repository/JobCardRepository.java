package com.garage.repository;

import com.garage.entity.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;
import com.garage.entity.JobCardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {
    // Find all job cards with pagination
    Page<JobCard> findByStatus(JobCardStatus status, Pageable pageable);
    // find all job cards created by a particular driver
    List<JobCard> findByDriverId(Long driverId);
    // find all job cards for a particular vehicle
    List<JobCard> findByVehicleId(Long vehicleId);
    // find all job cards with a specific status
    List<JobCard> findByStatus(String status);
    // Add more custom query methods as needed
    // Find all job cards assigned to a specific mechanic
    List<JobCard> findByMechanic_Id(Long mechanicId);
    // Find all job cards for a specific department
    List<JobCard> findByDepartmentId(Long departmentId);
}
