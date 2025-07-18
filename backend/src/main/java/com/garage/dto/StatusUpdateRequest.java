package com.garage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StatusUpdateRequest {
    @NotBlank(message = "status is required")
    private String status;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    // getters & setters
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}