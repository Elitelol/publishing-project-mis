package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    Optional<Publication> findPublicationById(Long Id);

    List<Publication> findPublicationsByManagerIdIsNull();
}
