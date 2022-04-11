package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT p from Publication p where p.id = :authorId")
    List<Publication> findPublicationsByAuthorsId(Long authorId);

    List<Publication> findPublicationsByManagerId(Long managerId);

    List<Publication> findPublicationsByProgressStatus(ProgressStatus progress);
}
