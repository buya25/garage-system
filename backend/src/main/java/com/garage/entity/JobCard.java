package com.garage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_cards")
public class JobCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who created this job card (driver user id)
    @Column(nullable = false)
    private Long driverId;

    // vehicle identifier (could be license plate, VIN, etc)
    @Column(nullable = false)
    private String vehicleId;


    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private User mechanic;


    @Column(nullable = false, length = 1000)
    private String description;

    // NEW → APPROVED → ASSIGNED → IN_PROGRESS → COMPLETED → CLOSED
    @Column(nullable = false)
    private String status = "NEW";

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean requiresParts = false;

    @Column(nullable = false)
    private Boolean approved = false;

    public JobCard() {}

    public JobCard(Long driverId, String vehicleId, String description) {
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.description = description;
        this.status = "NEW";
        this.createdAt = LocalDateTime.now();
        this.requiresParts = false;
        this.approved = false;
    }

    // getters & setters omitted for brevity
    // generate via your IDE or Lombok if you prefer

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDriverId() {
        return driverId;
    }
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
    public String getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getRequiresParts() {
        return requiresParts;
    }
    public void setRequiresParts(Boolean requiresParts) {
        this.requiresParts = requiresParts;
    }
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public User getMechanic() {
        return mechanic;
    }

    public void setMechanic(User mechanic) {
        this.mechanic = mechanic;
    }
}