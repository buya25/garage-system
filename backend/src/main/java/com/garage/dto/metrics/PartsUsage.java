package com.garage.dto.metrics;


public class PartsUsage {
    private String partNumber;
    private long totalConsumed;
    // constructor, getters
    public PartsUsage(String partNumber, long totalConsumed) {
        this.partNumber = partNumber;
        this.totalConsumed = totalConsumed;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public long getTotalConsumed() {
        return totalConsumed;
    }
}