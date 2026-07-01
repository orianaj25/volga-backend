package com.pedidos.mayorista.controller;

import com.pedidos.mayorista.dto.DashboardDTO;
import com.pedidos.mayorista.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard")
    public DashboardDTO dashboard(){

        return dashboardService.obtenerDashboard();

    }

}