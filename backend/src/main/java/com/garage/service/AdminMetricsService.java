package com.garage.service;

import org.springframework.stereotype.Service;
import com.garage.repository.JobCardRepository;
import com.garage.repository.AssignmentRepository;
import com.garage.repository.PartRepository;
import com.garage.dto.metrics.JobCountByStatus;
import com.garage.dto.metrics.PartsUsage;
import jakarta.persistence.EntityManager;
import java.util.List;

// Add your DTOs for JobCountByStatus and PartsUsage if needed
// import com.garage.dto.JobCountByStatus;
// import com.garage.dto.PartsUsage;

@Service
public class AdminMetricsService {
    private final JobCardRepository jobRepo;
    private final AssignmentRepository assignmentRepo;
    private final PartRepository partRepo;
    private final EntityManager em; // for custom queries

    public AdminMetricsService(JobCardRepository jobRepo,
                               AssignmentRepository assignmentRepo,
                               PartRepository partRepo,
                               EntityManager em) {
        this.jobRepo = jobRepo;
        this.assignmentRepo = assignmentRepo;
        this.partRepo = partRepo;
        this.em = em;
    }

    public List<JobCountByStatus> getJobCountsByStatus() {
        String q = "SELECT jc.status as status, COUNT(jc) as count FROM JobCard jc GROUP BY jc.status";
        return em.createQuery(q, JobCountByStatus.class).getResultList();
    }

    public List<PartsUsage> getTopPartsUsage() {
        String q = "SELECT p.partNumber as partNumber, SUM(a.quantity) as totalConsumed " +
                   "FROM PartUsageLog a GROUP BY p.partNumber ORDER BY totalConsumed DESC";
        return em.createQuery(q, PartsUsage.class).setMaxResults(10).getResultList();
    }
    
    // Add methods for other metrics as needed
    public List<JobCountByStatus> getJobCountsByMechanic() {
        String q = "SELECT a.mechanic_id as mechanic_id, COUNT(a.mechanic_id) as count FROM garage_db.assignments a GROUP BY a.mechanic_id";
        return em.createQuery(q, JobCountByStatus.class).getResultList();
    }
    // Add methods for other metrics as needed
    public List<JobCountByStatus> getJobCountsByDriver() {
        String q = "SELECT a.driver_id as driver_id, COUNT(a.driver_id) as count FROM garage_db.assignments a GROUP BY a.driver_id";
        return em.createQuery(q, JobCountByStatus.class).getResultList();
    }
}