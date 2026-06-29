package com.example.splitbill.controller;

import com.example.splitbill.dto.DashboardResponse;
import com.example.splitbill.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/{groupId}")
    public DashboardResponse getDashboard(@PathVariable Long groupId) {
        return dashboardService.getDashboard(groupId);
    }
}
