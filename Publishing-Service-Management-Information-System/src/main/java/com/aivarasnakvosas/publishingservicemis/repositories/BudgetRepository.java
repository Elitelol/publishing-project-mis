package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface BudgetRepository extends JpaRepository<PublishingBudget, Long> {
}
