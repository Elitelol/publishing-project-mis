package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findContractByPublicationId(Long publicationId);
}
