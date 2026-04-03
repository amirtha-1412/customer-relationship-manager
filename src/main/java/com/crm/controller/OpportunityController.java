package com.crm.controller;

import com.crm.entity.Opportunity;
import com.crm.service.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;

    @Autowired
    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    // GET /opportunities - Display all opportunities
    @GetMapping
    public String getAllOpportunities(Model model) {
        model.addAttribute("opportunities", opportunityService.getAllOpportunities());
        model.addAttribute("pageTitle", "Opportunities");
        return "opportunities/list";
    }

    // GET /opportunities/new - Show form to add a new opportunity
    @GetMapping("/new")
    public String showNewOpportunityForm(Model model) {
        model.addAttribute("opportunity", new Opportunity());
        model.addAttribute("pageTitle", "Add Opportunity");
        return "opportunities/form";
    }

    // POST /opportunities - Save new opportunity to database
    @PostMapping
    public String saveOpportunity(@ModelAttribute("opportunity") Opportunity opportunity) {
        opportunityService.saveOpportunity(opportunity);
        return "redirect:/opportunities";
    }

    // GET /opportunities/edit/{id} - Show edit form for existing opportunity
    @GetMapping("/edit/{id}")
    public String showEditOpportunityForm(@PathVariable Long id, Model model) {
        Optional<Opportunity> opportunity = opportunityService.getOpportunityById(id);
        if (opportunity.isPresent()) {
            model.addAttribute("opportunity", opportunity.get());
            model.addAttribute("pageTitle", "Edit Opportunity");
            return "opportunities/form";
        }
        return "redirect:/opportunities";
    }

    // POST /opportunities/edit/{id} - Update existing opportunity
    @PostMapping("/edit/{id}")
    public String updateOpportunity(@PathVariable Long id,
                                    @ModelAttribute("opportunity") Opportunity opportunity) {
        opportunity.setId(id);
        opportunityService.saveOpportunity(opportunity);
        return "redirect:/opportunities";
    }

    // GET /opportunities/delete/{id} - Delete opportunity
    @GetMapping("/delete/{id}")
    public String deleteOpportunity(@PathVariable Long id) {
        opportunityService.deleteOpportunity(id);
        return "redirect:/opportunities";
    }
}
