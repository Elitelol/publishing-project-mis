package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.BudgetComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aivaras Nakvosas
 */
public interface BudgetCommentRepository extends JpaRepository<BudgetComment, Long> {
}
