package com.garage.service;

import com.garage.entity.JobCard;
import com.garage.repository.JobCardRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobCardService {

    private final JobCardRepository repo;

    public JobCardService(JobCardRepository repo) {
        this.repo = repo;
    }

    public JobCard create(JobCard card) {
        // you can add business logic here (e.g., validation)
        return repo.save(card);
    }

    public List<JobCard> findByDriver(Long driverId) {
        return repo.findByDriverId(driverId);
    }

    public JobCard approve(Long id, Boolean requiresParts) {
        JobCard card = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("JobCard not found for id: " + id));
        card.setRequiresParts(requiresParts);
        if (!requiresParts) {
            card.setApproved(true);
            card.setStatus("APPROVED");
        } else {
            card.setStatus("PENDING_PARTS");
        }
        return repo.save(card);
    }
}
