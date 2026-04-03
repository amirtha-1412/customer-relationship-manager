package com.crm.service;

import com.crm.entity.Interaction;
import com.crm.repository.InteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionService {

    private final InteractionRepository interactionRepository;

    @Autowired
    public InteractionService(InteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    public Interaction saveInteraction(Interaction interaction) {
        return interactionRepository.save(interaction);
    }

    public List<Interaction> getAllInteractions() {
        return interactionRepository.findAll();
    }

    public List<Interaction> getInteractionsByCustomerId(Long customerId) {
        return interactionRepository.findByCustomerId(customerId);
    }

    public Optional<Interaction> getInteractionById(Long id) {
        return interactionRepository.findById(id);
    }

    public void deleteInteraction(Long id) {
        interactionRepository.deleteById(id);
    }
}
