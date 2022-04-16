package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.AbstractBasicEntity;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Genre;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Language;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.enums.PublicationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class PublicationDTOMapper {

    @Autowired
    private AttachmentDTOMapper attachmentDTOMapper;
    @Autowired
    private ContractDTOMapper contractDTOMapper;
    @Autowired
    private BudgetDTOMapper budgetDTOMapper;
    @Autowired
    private TaskDTOMapper taskDTOMapper;

    public void mapToPublication(Publication publication, PublicationDTO publicationDTO, List<User> authors) {
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
    }

    public PublicationDTO mapToDTO(Publication publication) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setPublicationId(publication.getId());
        publicationDTO.setAuthorId(publication.getAuthors().stream()
                .map(AbstractBasicEntity::getId)
                .collect(Collectors.toList()));
        publicationDTO.setName(publication.getName());
        publicationDTO.setPublicationType(publication.getPublicationType().toString());
        publicationDTO.setProgressStatus(publication.getProgressStatus().toString());
        publicationDTO.setRejectionReason(publication.getRejectionReason());
        publicationDTO.setIsbn((publication.getIsbn()));
        publicationDTO.setPageNumber(publication.getPageNumber());
        publicationDTO.setLanguage(publication.getLanguage().toString());
        publicationDTO.setGenre(publication.getGenre().toString());
        publicationDTO.setPrice(publication.getPrice());
        publicationDTO.setPublishDate(publication.getPublishDate());
        if (publication.getManager() != null) {
            publicationDTO.setManagerId(publication.getManager().getId());
        }
        publicationDTO.setAttachments(publication.getAttachments().stream()
                .map(attachment -> attachmentDTOMapper.mapToDTO(attachment))
                .collect(Collectors.toList()));
        if (publication.getContract() != null) {
            publicationDTO.setContract(contractDTOMapper.mapToDTO(publication.getContract(), publication));
        }
        if (publication.getPublishingBudget() != null) {
            publicationDTO.setBudget(budgetDTOMapper.mapToDTO(publication.getPublishingBudget(), publication));
        }
        publicationDTO.setTasks(publication.getTasks().stream()
                .map(task -> taskDTOMapper.mapToDTO(task))
                .collect(Collectors.toList()));
        return publicationDTO;
    }
}
