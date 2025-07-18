package com.garage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_card_id")
    private JobCard jobCard;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private Long mechanicId;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String status = "ASSIGNED";

    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Column(nullable = false)
    private String progressStatus = "PENDING"; // PENDING, IN_PROGRESS, COMPLETED

    @Column(length = 1000)
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobCard getJobCard() {
        return jobCard;
    }

    public void setJobCard(JobCard jobCard) {
        this.jobCard = jobCard;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
    
    public String getProgressStatus() {
        return progressStatus;
    }
    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}