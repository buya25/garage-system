package com.garage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_card_history")
public class JobCardHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long jobCardId;

    @Column(nullable = false)
    private Long userId; // The user who performed the action

    @Column(nullable = false)
    private String username;  // Added for better reporting

    @Column(nullable = false)
    private String role;      // Role of the user (ADMIN, STAFF, etc.)

    @Column(nullable = false)
    private String action; // e.g., "CREATED", "APPROVED", "STATUS_UPDATED", "CLOSED"

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public JobCardHistory() {}

    public JobCardHistory(Long jobCardId, Long userId, String username, String role, String action) {
        this.jobCardId = jobCardId;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public Long getJobCardId() { return jobCardId; }
    public Long getUserId() { return userId; }
    public String getAction() { return action; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}
