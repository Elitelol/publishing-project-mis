package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.ContractComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aivaras Nakvosas
 */
public interface ContractCommentRepository extends JpaRepository<ContractComment, Long> {
}
