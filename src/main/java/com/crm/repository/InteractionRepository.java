package com.crm.repository;

import com.crm.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    List<Interaction> findByCustomerId(Long customerId);

    List<Interaction> findByType(String type);
}
