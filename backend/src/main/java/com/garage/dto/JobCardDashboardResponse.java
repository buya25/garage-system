package com.garage.dto;

import java.util.Map;

public class JobCardDashboardResponse {
    private Map<String, Long> statusCounts;       // {NEW=5, APPROVED=3, ...}
    private Map<String, Long> opened;             // {today=2, week=7, month=15, year=120}
    private Map<String, Long> closed;             // {today=1, week=5, month=10, year=100}
    private Map<String, Long> mechanicWorkload;   // {MechanicA=3, MechanicB=5}

    // Getters/Setters
    public Map<String, Long> getStatusCounts() { return statusCounts; }
    public void setStatusCounts(Map<String, Long> statusCounts) { this.statusCounts = statusCounts; }

    public Map<String, Long> getOpened() { return opened; }
    public void setOpened(Map<String, Long> opened) { this.opened = opened; }

    public Map<String, Long> getClosed() { return closed; }
    public void setClosed(Map<String, Long> closed) { this.closed = closed; }

    public Map<String, Long> getMechanicWorkload() { return mechanicWorkload; }
    public void setMechanicWorkload(Map<String, Long> mechanicWorkload) { this.mechanicWorkload = mechanicWorkload; }
}
