package com.garage.dto;


import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CreateJobCardRequest {
    @NotNull(message = "Driver ID is required")
    private Long driverId;

    @NotBlank(message = "Vehicle ID must not be blank")
    @Size(max = 100, message = "Vehicle ID cannot exceed 100 characters")
    private String vehicleId;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    // getters & setters
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
}