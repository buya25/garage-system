package com.garage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateStatusRequest {

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "NEW|APPROVED|PENDING_PARTS|ASSIGNED|IN_PROGRESS|COMPLETED|CLOSED",
            message = "Status must be one of: NEW, APPROVED, PENDING_PARTS, ASSIGNED, IN_PROGRESS, COMPLETED, CLOSED")
    private String status;

    public UpdateStatusRequest() {}

    public UpdateStatusRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
