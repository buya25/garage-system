package com.garage.service;

import com.garage.entity.JobCard;
import com.garage.repository.JobCardRepository;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import com.garage.dto.ApprovalRequest;
import com.garage.service.PartService;
import java.util.List;

@Service
public class JobCardService {

    private final JobCardRepository repo;
    private final PartService partService;
    private final NotificationService notificationService;

    public JobCardService(JobCardRepository repo, PartService partService, NotificationService notificationService) {
        this.repo = repo;
        this.partService = partService;
        this.notificationService = notificationService;
    }

    public List<JobCard> findByDriver(Long driverId) {
        return repo.findByDriverId(driverId);
    }

    public JobCard approve(Long id, ApprovalRequest request) {
        JobCard card = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("JobCard not found for id: " + id));
        card.setRequiresParts(request.getRequiresParts());

        if (!request.getRequiresParts()) {
            card.setApproved(true);
            card.setStatus("APPROVED");
        } else {
            boolean allAvailable = request.getParts().stream()
                .allMatch(pu -> partService.isAvailable(pu.getPartNumber(), pu.getQuantity()));
            if (allAvailable) {
                request.getParts().forEach(pu -> partService.consume(pu.getPartNumber(), pu.getQuantity()));
                card.setApproved(true);
                card.setStatus("APPROVED");
            } else {
                card.setStatus("PENDING_PARTS");
            }
        }
        JobCard updated = repo.save(card);
        // Send notification of approval or pending parts
        notificationService.sendApprovalNotification(updated);
        return updated;
    }

    public JobCard create(JobCard card) {
        return repo.save(card);
    }

    public List<JobCard> findAll() {
        return repo.findAll();
    }

    public JobCard getById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("JobCard not found for id: " + id));
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
