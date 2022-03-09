package com.aivarasnakvosas.publishingservicemis.Repository;

import com.aivarasnakvosas.publishingservicemis.Entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
