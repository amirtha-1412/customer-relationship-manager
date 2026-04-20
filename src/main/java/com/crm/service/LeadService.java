package com.crm.service;

import com.crm.entity.Lead;
import com.crm.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Page<Lead> getLeadsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return leadRepository.findAll(pageable);
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

    public List<Lead> searchLeads(String keyword) {
        Set<Lead> results = new LinkedHashSet<>();
        results.addAll(leadRepository.findByNameContainingIgnoreCase(keyword));
        results.addAll(leadRepository.findByEmailContainingIgnoreCase(keyword));
        return List.copyOf(results);
    }
}


