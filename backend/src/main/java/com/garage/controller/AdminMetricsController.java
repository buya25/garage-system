package com.garage.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.garage.service.AdminMetricsService;
import com.garage.dto.metrics.JobCountByStatus;
import com.garage.dto.metrics.PartsUsage;
import java.util.List;

@RestController
@RequestMapping("/api/admin/metrics")
public class AdminMetricsController {
    private final AdminMetricsService metricsService;
    
    // Constructor injection for AdminMetricsService
    public AdminMetricsController(AdminMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    // Endpoint to get job counts by status
    @GetMapping("/jobs-by-status")
    public List<JobCountByStatus> jobsByStatus() {
        return metricsService.getJobCountsByStatus();
    }

    // Endpoint to get top parts usage
    @GetMapping("/top-parts")
    public List<PartsUsage> topParts() {
        return metricsService.getTopPartsUsage();
    }

    // Endpoint to get job counts by mechanic
    @GetMapping("/jobs-by-mechanic")
    public List<JobCountByStatus> jobsByMechanic() {
        return metricsService.getJobCountsByMechanic();
    }

    // Endpoint to get job counts by driver
    @GetMapping("/jobs-by-driver")
    public List<JobCountByStatus> jobsByDriver() {
        return metricsService.getJobCountsByDriver();
    }

    // Endpoint to get job counts by vehicle
    @GetMapping("/jobs-by-vehicle")
    public List<JobCountByStatus> jobsByVehicle() {
        return metricsService.getJobCountsByVehicle();
    }

    // Endpoint to get job counts by department
    @GetMapping("/jobs-by-department")
    public List<JobCountByStatus> jobsByDepartment() {
        return metricsService.getJobCountsByDepartment();
    }

    // Endpoint to get job counts by date range
    @GetMapping("/jobs-by-date")
    public List<JobCountByStatus> jobsByDate(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
        return metricsService.getJobCountsByDateRange(startDate, endDate);
    }
}