package com.garage.dto.metrics;

public class JobCountByStatus {
    private String status;
    private Long count;

    public JobCountByStatus(String status, Long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public Long getCount() {
        return count;
    }
}
