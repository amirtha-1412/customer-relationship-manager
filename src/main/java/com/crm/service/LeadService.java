package com.crm.service;

import com.crm.entity.Lead;
import com.crm.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    @Autowired
    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Lead saveLead(Lead lead) {
        return leadRepository.save(lead);
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public Optional<Lead> getLeadById(Long id) {
        return leadRepository.findById(id);
    }

    public void deleteLead(Long id) {
        leadRepository.deleteById(id);
    }

    public Optional<Lead> updateLeadStatus(Long id, String status) {
        Optional<Lead> existing = leadRepository.findById(id);
        if (existing.isPresent()) {
            Lead lead = existing.get();
            lead.setStatus(status);
            leadRepository.save(lead);
            return Optional.of(lead);
        }
        return Optional.empty();
    }
}
