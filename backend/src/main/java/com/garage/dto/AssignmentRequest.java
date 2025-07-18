package com.garage.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import com.garage.entity.JobCard;
import java.util.List;
/**
 * DTO for assignment requests
 */

public class AssignmentRequest {
    @NotNull(message = "jobCardId is required")
    private Long jobCardId;

    @NotBlank(message = "department is required")
    private String department;

    @NotNull(message = "mechanicId is required")
    private Long mechanicId;

    @NotBlank(message = "priority is required")
    private String priority;

    // getters and setters
    public Long getJobCardId() {
        return jobCardId;
    }

    public void setJobCardId(Long jobCardId) {
        this.jobCardId = jobCardId;
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
}