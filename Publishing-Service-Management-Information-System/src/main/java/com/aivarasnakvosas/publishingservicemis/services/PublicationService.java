package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.mappers.PublicationDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PublicationDTOMapper publicationDTOMapper;

    public Publication getPublication(Long id) {
        return publicationRepository.getById(id);
    }

    public Publication savePublication(PublicationDTO publicationDTO) {
        User author = userService.getUser(publicationDTO.getUserId());
        Publication publication = new Publication();
        publication = publicationDTOMapper.mapToPublication(publication, publicationDTO, author);
        return publicationRepository.save(publication);
    }

    public Publication addManager(Long publicationId, Long managerId) {
        User manager = userService.getUser(managerId);
        Publication publication = getPublication(publicationId);
        publication.setManager(manager);
        return publicationRepository.save(publication);
    }
}
