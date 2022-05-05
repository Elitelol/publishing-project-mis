package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface BudgetRepository extends JpaRepository<PublishingBudget, Long> {

    Optional<PublishingBudget> findPublishingBudgetByPublicationId(Long publicationId);
}
