package com.garage.service;

import com.garage.entity.JobCard;
import com.garage.entity.JobCardStatus;
import com.garage.repository.JobCardRepository;
import com.garage.repository.OffroadRepository;
import com.garage.repository.UserRepository;
import com.garage.dto.JobCardDashboardResponse;
import com.garage.repository.JobCardHistoryRepository;
import com.garage.service.PartService;
import com.garage.service.NotificationService;
import com.garage.dto.ApprovalRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import com.garage.entity.Role;
import com.garage.entity.User;
import com.garage.dto.ApprovalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class JobCardService {

    private final JobCardRepository repo;
    private final PartService partService;
    private final NotificationService notificationService;
    private final OffroadRepository offRepo;
    private final JobCardHistoryRepository historyRepo;

    public JobCardService(JobCardRepository repo,
                          PartService partService,
                          NotificationService notificationService,
                          OffroadRepository offRepo,
                          JobCardHistoryRepository historyRepo) {
        this.repo = repo;
        this.partService = partService;
        this.notificationService = notificationService;
        this.offRepo = offRepo;
        this.historyRepo = historyRepo;
    }

    // Assign a mechanic to a job card
    public JobCard assignMechanic(Long jobCardId, Long mechanicId) {
        JobCard jobCard = getById(jobCardId);
        User mechanic = userRepo.findById(mechanicId)
                .orElseThrow(() -> new EntityNotFoundException("Mechanic not found"));

        if (mechanic.getRole() != Role.ROLE_MECHANIC) {
            throw new IllegalArgumentException("User is not a mechanic");
        }

        jobCard.setMechanic(mechanic);
        jobCard.setStatus(JobCardStatus.ASSIGNED); // Status moves to ASSIGNED
        return repo.save(jobCard);
    }

    // Find all job cards with pagination
    public Page<JobCard> findByPage(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repo.findAll(pageable);
    }

    // Find job cards assigned to a specific mechanic
    public List<JobCard> findByMechanic(Long mechanicId) {
        return repo.findAll().stream()
            .filter(card -> card.getMechanic() != null && card.getMechanic().getId().equals(mechanicId))
            .toList();
    }


    // Create new job card
    public JobCard create(JobCard card, Long userId) {
        JobCard saved = repo.save(card);
        // Notify the user about the creation of the job card
        notificationService.sendCreationNotification(saved);
        // Log the creation in history
        historyRepo.save(new JobCardHistory(saved.getId(), userId, "CREATED"));
        return saved;
    }

    // Find all job cards
    public List<JobCard> findAll() {
        return repo.findAll();
    }

    // Find by driver ID
    public List<JobCard> findByDriver(Long driverId) {
        return repo.findByDriverId(driverId);
    }

    // Find by vehicle ID
    public List<JobCard> findByVehicle(String vehicleId) {
        return repo.findByVehicleId(vehicleId);
    }

    // Find by status
    public List<JobCard> findByStatus(JobCardStatus status) {
        return repo.findByStatus(status);
    }

    // Pagination support
    public Page<JobCard> findByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

    // Check if driver has any open job card (not closed)
    public boolean hasOpenJobCards(Long driverId) {
        List<JobCard> cards = repo.findByDriverId(driverId);
        return cards.stream().anyMatch(card -> card.getStatus() != JobCardStatus.CLOSED);
    }

    // Get a job card by ID
    public JobCard getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobCard not found for id: " + id));
    }

    // Approve job card
    public JobCard approve(Long id, ApprovalRequest request, Long userId) {
        JobCard card = getById(id);
        card.setRequiresParts(request.getRequiresParts());

        if (!request.getRequiresParts()) {
            card.setApproved(true);
            card.setStatus(JobCardStatus.APPROVED);
        } else {
            boolean allAvailable = request.getParts().stream()
                    .allMatch(pu -> partService.isAvailable(pu.getPartNumber(), pu.getQuantity()));

            if (allAvailable) {
                request.getParts().forEach(pu -> partService.consume(pu.getPartNumber(), pu.getQuantity()));
                card.setApproved(true);
                card.setStatus(JobCardStatus.APPROVED);
            } else {
                card.setStatus(JobCardStatus.PENDING_PARTS);
            }
        }

        JobCard updated = repo.save(card);
        historyRepo.save(new JobCardHistory(id, userId, "APPROVED"));
        return updated;
    }

    // Delete a job card
    public void delete(Long id, Long userId) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("JobCard not found for id: " + id);
        }
        repo.deleteById(id);
        historyRepo.save(new JobCardHistory(id, userId, "DELETED"));
    }

    // Update status
    public JobCard updateStatus(Long id, JobCardStatus status, Long userId) {
        JobCard card = getById(id);
        card.setStatus(status);
        JobCard updated = repo.save(card);
        historyRepo.save(new JobCardHistory(id, userId, "STATUS_UPDATED: " + status.name()));
        return updated;
    }


    // Get dashboard summary
    public JobCardDashboardResponse getDashboardSummary() {
        List<JobCard> allCards = repo.findAll();
        JobCardDashboardResponse response = new JobCardDashboardResponse();

        // 1. Status counts
        Map<String, Long> statusCounts = allCards.stream()
            .collect(Collectors.groupingBy(card -> card.getStatus().name(), Collectors.counting()));

        // 2. Time-based counts
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1).toLocalDate().atStartOfDay();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime startOfYear = now.withDayOfYear(1).toLocalDate().atStartOfDay();

        Map<String, Long> opened = new HashMap<>();
        opened.put("today", allCards.stream().filter(c -> c.getCreatedAt().isAfter(startOfDay)).count());
        opened.put("week", allCards.stream().filter(c -> c.getCreatedAt().isAfter(startOfWeek)).count());
        opened.put("month", allCards.stream().filter(c -> c.getCreatedAt().isAfter(startOfMonth)).count());
        opened.put("year", allCards.stream().filter(c -> c.getCreatedAt().isAfter(startOfYear)).count());

        Map<String, Long> closed = new HashMap<>();
        closed.put("today", allCards.stream()
            .filter(c -> c.getStatus() == JobCardStatus.CLOSED && c.getUpdatedAt().isAfter(startOfDay)).count());
        closed.put("week", allCards.stream()
            .filter(c -> c.getStatus() == JobCardStatus.CLOSED && c.getUpdatedAt().isAfter(startOfWeek)).count());
        closed.put("month", allCards.stream()
            .filter(c -> c.getStatus() == JobCardStatus.CLOSED && c.getUpdatedAt().isAfter(startOfMonth)).count());
        closed.put("year", allCards.stream()
            .filter(c -> c.getStatus() == JobCardStatus.CLOSED && c.getUpdatedAt().isAfter(startOfYear)).count());

        // 3. Mechanic workload
        Map<String, Long> mechanicWorkload = allCards.stream()
            .filter(card -> card.getMechanic() != null)
            .collect(Collectors.groupingBy(
                card -> card.getMechanic().getUsername(),
                Collectors.counting()
            ));

        response.setStatusCounts(statusCounts);
        response.setOpened(opened);
        response.setClosed(closed);
        response.setMechanicWorkload(mechanicWorkload);
        return response;
    }
}
