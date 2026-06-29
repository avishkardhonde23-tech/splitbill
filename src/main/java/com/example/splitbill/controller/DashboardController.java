package com.example.splitbill.controller;

import com.example.splitbill.dto.DashboardResponse;
import com.example.splitbill.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard API", description = "Dashboard summary")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @Operation(summary = "Get dashboard details")
    @GetMapping("/{groupId}")
    public DashboardResponse getDashboard(@PathVariable Long groupId) {
        return dashboardService.getDashboard(groupId);
    }
}
