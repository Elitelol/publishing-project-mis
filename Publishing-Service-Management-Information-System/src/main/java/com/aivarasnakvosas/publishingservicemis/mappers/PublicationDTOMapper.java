package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.utilities.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.utilities.PublicationType;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class PublicationDTOMapper {

    public Publication mapToPublication(Publication publication, PublicationDTO publicationDTO, User author) {
        if (publication.getId() == null){
            publication.setProgressStatus(ProgressStatus.NOT_STARTED);
            publication.setDateCreated(new Date());
        }
        publication.setDateModified(new Date());
        publication.setName(publicationDTO.getName());
        publication.setPublicationType(PublicationType.valueOf(publicationDTO.getPublicationType().toUpperCase()));
        publication.addAuthor(author);
        return publication;
    }
}
