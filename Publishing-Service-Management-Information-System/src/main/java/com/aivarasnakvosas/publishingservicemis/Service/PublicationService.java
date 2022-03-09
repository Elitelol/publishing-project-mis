package com.aivarasnakvosas.publishingservicemis.Service;

import com.aivarasnakvosas.publishingservicemis.Entity.Publication;
import com.aivarasnakvosas.publishingservicemis.Repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aivaras Nakvosas
 */
@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    public Publication getPublication(Long id) {
        return publicationRepository.getById(id);
    }

    public Publication savePublication(Publication publication) {
        return publicationRepository.save(publication);
    }
}
