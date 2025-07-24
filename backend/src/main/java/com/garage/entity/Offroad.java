package com.garage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offroad_records")
public class Offroad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // link back to the JobCard
    @Column(nullable = false, updatable = false)
    private Long jobCardId;

    // snapshot of vehicleId for quick lookups
    @Column(nullable = false, updatable = false)
    private String vehicleId;

    // when it was taken off-road
    @Column(nullable = false)
    private LocalDateTime offroadAt = LocalDateTime.now();

    // when staff/controller clears it
    private LocalDateTime clearedAt;

    // PENDING until cleared
    @Column(nullable = false)
    private String status = "PENDING";

    public Offroad() {}

    public Offroad(Long jobCardId, String vehicleId) {
        this.jobCardId = jobCardId;
        this.vehicleId = vehicleId;
    }

    // getters & setters…
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getJobCardId() {
        return jobCardId;
    }

    public void setJobCardId(Long jobCardId) {
        this.jobCardId = jobCardId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getOffroadAt() {
        return offroadAt;
    }

    public void setOffroadAt(LocalDateTime offroadAt) {
        this.offroadAt = offroadAt;
    }

    public LocalDateTime getClearedAt() {
        return clearedAt;
    }

    public void setClearedAt(LocalDateTime clearedAt) {
        this.clearedAt = clearedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
