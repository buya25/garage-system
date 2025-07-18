package com.garage.dto;


import java.io.Serializable;
import com.garage.entity.JobCard;
import jakarta.validation.constraints.Size;
import java.util.List;
import jakarta.validation.Valid;


public class ApprovalRequest {
    private Boolean requiresParts;

    public Boolean getRequiresParts() {
        return requiresParts;
    }

    public void setRequiresParts(Boolean requiresParts) {
        this.requiresParts = requiresParts;
    }

    /**
     * When requiresParts=true, list parts and quantities to check
     */
    @Size(min = 1, message = "At least one part must be specified when parts are required")
    private List<PartUsageRequest> parts;

    public List<PartUsageRequest> getParts() {
        return parts;
    }

    public void setParts(List<PartUsageRequest> parts) {
        this.parts = parts;
    }

}