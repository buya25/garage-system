package com.garage.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.garage.service.AdminMetricsService;
import com.garage.dto.metrics.JobCountByStatus;
import com.garage.dto.metrics.PartsUsage;
import java.util.List;

@RestController
@RequestMapping("/api/admin/metrics")
public class AdminMetricsController {
    private final AdminMetricsService metricsService;
    
    public AdminMetricsController(AdminMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/jobs-by-status")
    public List<JobCountByStatus> jobsByStatus() {
        return metricsService.getJobCountsByStatus();
    }

    @GetMapping("/top-parts")
    public List<PartsUsage> topParts() {
        return metricsService.getTopPartsUsage();
    }
}