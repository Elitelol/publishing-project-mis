package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.enums.PublicationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class PublicationDTOMapper {

    @Autowired
    private AttachmentDTOMapper attachmentDTOMapper;

    public Publication mapToPublication(Publication publication, PublicationDTO publicationDTO, User author) {
        if (publication.getId() == null){
            publication.setProgressStatus(ProgressStatus.NOT_STARTED);
            publication.setDateCreated(new Date());
        }
        publication.setDateModified(new Date());
        publication.setName(publicationDTO.getName());
        publication.setPublicationType(PublicationType.valueOf(publicationDTO.getPublicationType().toUpperCase()));
        publication.addAuthor(author);
        /*
        List<Attachment> attachments = publicationDTO
                .getAttachments()
                .stream()
                .map(attachmentDTO -> attachmentDTOMapper.mapToAttachment(attachmentDTO, publication))
                .collect(Collectors.toList());
        attachments.forEach(publication::addAttachment);

         */
        return publication;
    }
}
