package com.crm.service;

import com.crm.entity.Lead;
import com.crm.entity.Opportunity;
import com.crm.entity.Task;
import com.crm.repository.CustomerRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.OpportunityRepository;
import com.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;
    private final OpportunityRepository opportunityRepository;

    @Autowired
    public DashboardService(CustomerRepository customerRepository,
                            LeadRepository leadRepository,
                            TaskRepository taskRepository,
                            OpportunityRepository opportunityRepository) {
        this.customerRepository = customerRepository;
        this.leadRepository = leadRepository;
        this.taskRepository = taskRepository;
        this.opportunityRepository = opportunityRepository;
    }

    // ── Admin stats ──────────────────────────────────────────────
    public long getTotalCustomers() {
        return customerRepository.count();
    }

    public long getTotalLeads() {
        return leadRepository.count();
    }

    public long getTotalTasks() {
        return taskRepository.count();
    }

    public long getTotalOpportunities() {
        return opportunityRepository.count();
    }

    public double getTotalRevenue() {
        Double revenue = opportunityRepository.sumAmountByStage("WON");
        return revenue != null ? revenue : 0.0;
    }

    public List<Lead> getLatestLeads() {
        return leadRepository.findTop5ByOrderByIdDesc();
    }

    // ── Sales (my data) ──────────────────────────────────────────
    public List<Lead> getMyLeads(String username) {
        return leadRepository.findByAssignedTo(username);
    }

    public List<Task> getMyTasks(String username) {
        return taskRepository.findByAssignedTo(username);
    }

    public List<Opportunity> getMyOpportunities() {
        return opportunityRepository.findAll();
    }
}


