package com.garage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

public class PartUsageRequest {
    @NotBlank(message = "partNumber is required")
    private String partNumber;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;

    // getters & setters
    public PartUsageRequest() {}

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}