package com.garage.dto;

import com.garage.entity.JobCard;
import com.garage.entity.JobCardStatus;
import java.time.LocalDateTime;

public class JobCardResponse {

    private Long id;
    private Long driverId;
    private String vehicleId;
    private String description;
    private JobCardStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String mechanicName;

    public JobCardResponse(JobCard card) {
        this.id = card.getId();
        this.driverId = card.getDriverId();
        this.vehicleId = card.getVehicleId();
        this.description = card.getDescription();
        this.status = card.getStatus();
        this.createdAt = card.getCreatedAt();
        this.updatedAt = card.getUpdatedAt();
        this.mechanicName = card.getMechanic() != null ? card.getMechanic().getUsername() : null;
    }

    // Getters and setters (or use Lombok @Data if preferred)
    public Long getId() {
        return id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public JobCardStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getMechanicName() {
        return mechanicName;
    }
}
