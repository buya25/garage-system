package com.garage.service;

import com.garage.entity.JobCard;
import org.springframework.stereotype.Service;
import com.garage.entity.Assignment;
import com.garage.repository.JobCardRepository;
import com.garage.repository.AssignmentRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Notify subscribers when a job card's approval status changes
     */
    public void sendApprovalNotification(JobCard card) {
        // Send to destination /topic/jobcards/approval
        messagingTemplate.convertAndSend(
            "/topic/jobcards/approval",
            card
        );
    }

    /**
     * Notify subscribers when an assignment is created
     */
    public void sendAssignmentNotification(Assignment assignment) {
        messagingTemplate.convertAndSend(
            "/topic/assignments",
            assignment
        );
    }

    public void sendProgressNotification(Assignment assignment) {
    messagingTemplate.convertAndSend(
        "/topic/assignments/progress",
        assignment
    );
}
    // Additional notification methods can be added here
    // e.g., for job card creation, updates, etc.
    public void sendJobCardCreatedNotification(JobCard card) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendJobCardUpdatedNotification(JobCard card) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendJobCardDeletedNotification(JobCard card) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendPartAvailabilityNotification(String partNumber, int quantity) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendPartUsageNotification(String partNumber, int quantity) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendPartRestockNotification(String partNumber, int quantity) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendErrorNotification(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendGeneralNotification(String title, String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendCustomNotification(String type, String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendSystemNotification(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendUserNotification(Long userId, String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendMechanicNotification(Long mechanicId, String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendDriverNotification(Long driverId, String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendAdminNotification(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendNotificationToAll(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendNotificationToDepartment(String department, String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendNotificationToMechanics(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }

    public void sendNotificationToDrivers(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendNotificationToAdmins(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
    public void sendNotificationToUsers(String message) {
        // TODO: publish event or send WebSocket message to frontend clients
    }
}   