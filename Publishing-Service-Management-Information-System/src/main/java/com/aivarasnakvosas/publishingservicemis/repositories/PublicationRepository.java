package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    Optional<Publication> findPublicationById(Long Id);

    List<Publication> findPublicationsByManagerIsNullAndProgressStatus(ProgressStatus progressStatus);

    List<Publication> findPublicationsByAuthorsIn(Set<User> authors);

    List<Publication> findPublicationsByManagerId(Long managerId);

    List<Publication> findPublicationsByProgressStatus(ProgressStatus progress);
}
