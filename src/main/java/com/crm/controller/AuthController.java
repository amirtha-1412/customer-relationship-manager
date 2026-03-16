package com.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        // Return the login.html Thymeleaf template
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Return the dashboard.html Thymeleaf template
        model.addAttribute("pageTitle", "Dashboard");
        return "dashboard";
    }
}
