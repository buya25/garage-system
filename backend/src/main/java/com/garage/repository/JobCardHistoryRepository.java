package com.garage.repository;

import com.garage.entity.JobCardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobCardHistoryRepository extends JpaRepository<JobCardHistory, Long> {
    List<JobCardHistory> findByJobCardId(Long jobCardId);
}
