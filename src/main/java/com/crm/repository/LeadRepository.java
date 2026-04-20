package com.crm.repository;

import com.crm.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findByStatus(String status);

    List<Lead> findByAssignedTo(String assignedTo);

    List<Lead> findTop5ByOrderByIdDesc();

    List<Lead> findByNameContainingIgnoreCase(String keyword);

    List<Lead> findByEmailContainingIgnoreCase(String keyword);
}

