package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.utilities.AttachmentType;
import com.aivarasnakvosas.publishingservicemis.entity.utilities.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.mappers.PublicationDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Service
@Transactional
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PublicationDTOMapper publicationDTOMapper;
    @Autowired
    private AttachmentService attachmentService;

    public Publication getPublication(Long id) {
        Optional<Publication> publication = publicationRepository.findPublicationById(id);
        return publication.orElse(null);
    }

    public Publication savePublication(PublicationDTO publicationDTO) {
        User author = userService.getUser(publicationDTO.getUserId());
        Optional<Publication> existingPublication = publicationRepository.findPublicationById(publicationDTO.getPublicationId());
        Publication publication = existingPublication.orElseGet(Publication::new);
        publication = publicationDTOMapper.mapToPublication(publication, publicationDTO, author);
        resolveManuscriptAttachments(publicationDTO.getAttachments(), publication);
        return publicationRepository.save(publication);
    }

    private void resolveManuscriptAttachments(List<AttachmentDTO> attachmentDTOS, Publication publication) {
        boolean allManuscripts = attachmentDTOS
                .stream()
                .allMatch(attachmentDTO -> attachmentDTO.getAttachmentType().equals(AttachmentType.MANUSCRIPT.name()));
        if (publication.getId() == null && allManuscripts) {
            attachmentDTOS.forEach(attachmentDTO ->attachmentService.saveAttachment(attachmentDTO, publication));
        } else {
            throw new RuntimeException();
        }
    }

    public Publication changePublicationStatus(PublicationAcceptanceDTO publicationAcceptanceDTO) {
        Optional<Publication> publication = publicationRepository.findPublicationById(publicationAcceptanceDTO.getPublicationId());
        if (publication.isEmpty()) {
            throw new RuntimeException();
        }
        return setPublicationStatus(publication.get(), publicationAcceptanceDTO);
    }

    private Publication setPublicationStatus(Publication publication, PublicationAcceptanceDTO publicationAcceptanceDTO) {
        if (!publication.getManager().getId().equals(publicationAcceptanceDTO.getManagerId())) {
            throw new RuntimeException();
        }
        publication.setProgressStatus(ProgressStatus.valueOf(publicationAcceptanceDTO.getStatus()));
        publication.setDateModified(new Date());
        return publicationRepository.save(publication);
    }

    public Publication addManager(Long publicationId, Long managerId) {
        User manager = userService.getUser(managerId);
        Publication publication = getPublication(publicationId);
        publication.setManager(manager);
        return publicationRepository.save(publication);
    }

    public List<Publication> getUnmanagedPublications() {
        return publicationRepository.findPublicationsByManagerIdIsNull();
    }
}
