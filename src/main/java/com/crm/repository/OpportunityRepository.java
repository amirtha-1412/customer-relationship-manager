package com.crm.repository;

import com.crm.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    List<Opportunity> findByStage(String stage);

    List<Opportunity> findByCustomerId(Long customerId);

    @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Opportunity o WHERE o.stage = :stage")
    Double sumAmountByStage(@Param("stage") String stage);
}
