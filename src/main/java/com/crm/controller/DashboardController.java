package com.crm.controller;

import com.crm.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("username", username);

        if (isAdmin) {
            // Admin: full CRM statistics
            model.addAttribute("totalCustomers",    dashboardService.getTotalCustomers());
            model.addAttribute("totalLeads",        dashboardService.getTotalLeads());
            model.addAttribute("totalTasks",        dashboardService.getTotalTasks());
            model.addAttribute("totalOpportunities",dashboardService.getTotalOpportunities());
            model.addAttribute("totalRevenue",      dashboardService.getTotalRevenue());
            model.addAttribute("latestLeads",       dashboardService.getLatestLeads());
        } else {
            // Sales: only assigned data
            model.addAttribute("myLeads",           dashboardService.getMyLeads(username));
            model.addAttribute("myTasks",           dashboardService.getMyTasks(username));
            model.addAttribute("myOpportunities",   dashboardService.getMyOpportunities());
        }

        return "dashboard";
    }
}
