package com.crm.controller;

import com.crm.entity.Interaction;
import com.crm.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/interactions")
public class InteractionController {

    private final InteractionService interactionService;

    @Autowired
    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    // GET /interactions - Display all interactions
    @GetMapping
    public String getAllInteractions(Model model) {
        model.addAttribute("interactions", interactionService.getAllInteractions());
        model.addAttribute("pageTitle", "Interactions");
        return "interactions/list";
    }

    // GET /interactions/new - Show form to add a new interaction
    @GetMapping("/new")
    public String showNewInteractionForm(Model model) {
        model.addAttribute("interaction", new Interaction());
        model.addAttribute("pageTitle", "Log Interaction");
        return "interactions/form";
    }

    // POST /interactions - Save new interaction to database
    @PostMapping
    public String saveInteraction(@ModelAttribute("interaction") Interaction interaction) {
        interactionService.saveInteraction(interaction);
        return "redirect:/interactions";
    }

    // GET /interactions/delete/{id} - Delete interaction
    @GetMapping("/delete/{id}")
    public String deleteInteraction(@PathVariable Long id) {
        interactionService.deleteInteraction(id);
        return "redirect:/interactions";
    }
}
