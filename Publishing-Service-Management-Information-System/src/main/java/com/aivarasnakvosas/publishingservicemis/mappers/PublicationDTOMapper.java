package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Genre;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Language;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.enums.PublicationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class PublicationDTOMapper {

    @Autowired
    private AttachmentDTOMapper attachmentDTOMapper;

    public Publication mapToPublication(Publication publication, PublicationDTO publicationDTO, List<User> authors) {
        if (publication.getId() == null){
            publication.setProgressStatus(ProgressStatus.NOT_STARTED);
        }
        publication.setName(publicationDTO.getName());
        publication.setIsbn(publicationDTO.getIsbn());
        publication.setPageNumber(publicationDTO.getPageNumber());
        publication.setLanguage(Language.valueOf(publicationDTO.getLanguage()));
        publication.setGenre(Genre.valueOf(publicationDTO.getGenre()));
        publication.setPublishDate(publicationDTO.getPublishDate());
        publication.setPublicationType(PublicationType.valueOf(publicationDTO.getPublicationType().toUpperCase()));
        authors.forEach(publication::addAuthor);
        return publication;
    }

    public PublicationDTO mapToDTO(Publication publication) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setName(publication.getName());
        publicationDTO.setIsbn((publication.getIsbn()));
        publicationDTO.setPageNumber(publication.getPageNumber());
        publicationDTO.setLanguage(publication.getLanguage().toString());
        publicationDTO.setGenre(publication.getGenre().toString());
        publicationDTO.setPublishDate(publication.getPublishDate());
        publicationDTO.setPublicationType(publication.getPublicationType().toString());
        return publicationDTO;
    }
}
