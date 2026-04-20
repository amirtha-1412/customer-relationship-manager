package com.crm.controller;

import com.crm.entity.Lead;
import com.crm.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/leads")
public class LeadController {

    private final LeadService leadService;

    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    // GET /leads?page=0&size=10&keyword=
    @GetMapping
    public String getAllLeads(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search mode — no pagination
            List<Lead> leads = leadService.searchLeads(keyword.trim());
            model.addAttribute("leads", leads);
            model.addAttribute("leadPage", null);
        } else {
            // Paginated mode
            Page<Lead> leadPage = leadService.getLeadsPaged(page, size);
            model.addAttribute("leads", leadPage.getContent());
            model.addAttribute("leadPage", leadPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", leadPage.getTotalPages());
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Leads");
        return "leads/list";
    }



    // GET /leads/new - Show form to create a new lead
    @GetMapping("/new")
    public String showNewLeadForm(Model model) {
        model.addAttribute("lead", new Lead());
        model.addAttribute("pageTitle", "Add Lead");
        return "leads/form";
    }

    // POST /leads - Save new lead to database
    @PostMapping
    public String saveLead(@ModelAttribute("lead") Lead lead) {
        leadService.saveLead(lead);
        return "redirect:/leads";
    }

    // GET /leads/edit/{id} - Show edit form for existing lead
    @GetMapping("/edit/{id}")
    public String showEditLeadForm(@PathVariable Long id, Model model) {
        Optional<Lead> lead = leadService.getLeadById(id);
        if (lead.isPresent()) {
            model.addAttribute("lead", lead.get());
            model.addAttribute("pageTitle", "Edit Lead");
            return "leads/form";
        }
        return "redirect:/leads";
    }

    // POST /leads/edit/{id} - Update existing lead
    @PostMapping("/edit/{id}")
    public String updateLead(@PathVariable Long id,
                             @ModelAttribute("lead") Lead lead) {
        lead.setId(id);
        leadService.saveLead(lead);
        return "redirect:/leads";
    }

    // GET /leads/delete/{id} - Delete lead
    @GetMapping("/delete/{id}")
    public String deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
        return "redirect:/leads";
    }
}
