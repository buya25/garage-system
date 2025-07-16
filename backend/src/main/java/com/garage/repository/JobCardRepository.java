package com.garage.repository;

import com.garage.entity.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {
    // find all job cards created by a particular driver
    List<JobCard> findByDriverId(Long driverId);
}
