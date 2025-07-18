package com.garage.service;

import com.garage.dto.AssignmentRequest;
import com.garage.entity.Assignment;
import com.garage.entity.JobCard;
import com.garage.repository.AssignmentRepository;
import com.garage.repository.JobCardRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository repo;
    private final JobCardRepository jobCardRepo;
    private final NotificationService notificationService;

    public AssignmentService(AssignmentRepository repo, JobCardRepository jobCardRepo, NotificationService notificationService) {
        this.repo = repo;
        this.jobCardRepo = jobCardRepo;
        this.notificationService = notificationService;
    }

    public Assignment assignToMechanic(AssignmentRequest req) {
        JobCard card = jobCardRepo.findById(req.getJobCardId())
            .orElseThrow(() -> new EntityNotFoundException("JobCard not found: " + req.getJobCardId()));

        if (!card.getApproved()) {
            throw new IllegalStateException("JobCard must be approved before assignment");
        }

        Assignment assignment = new Assignment();
        assignment.setJobCard(card);
        assignment.setDepartment(req.getDepartment());
        assignment.setMechanicId(req.getMechanicId());
        assignment.setPriority(req.getPriority());

        Assignment saved = repo.save(assignment);
        notificationService.sendAssignmentNotification(saved);
        return saved;
    }

    public List<Assignment> listByMechanic(Long mechanicId) {
        return repo.findByMechanicId(mechanicId);
    }

    public List<Assignment> listByDriver(Long driverId) {
        return repo.findByJobCard_DriverId(driverId);
    }
}