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
    @Autowired
    private UserDTOMapper userDTOMapper;

    public void mapToPublication(Publication publication, PublicationDTO publicationDTO, User author) {
        if (publication.getId() == null){
            publication.setProgressStatus(ProgressStatus.NOT_SUBMITTED);
        }
        publication.setName(publicationDTO.getName());
        publication.setIsbn(publicationDTO.getIsbn());
        publication.setPageNumber(publicationDTO.getPageNumber());
        publication.setLanguage(Language.getLanguage(publicationDTO.getLanguage()));
        publication.setGenre(Genre.getGenre(publicationDTO.getGenre()));
        publication.setPublishDate(publicationDTO.getPublishDate());
        publication.setPublicationType(PublicationType.getType(publicationDTO.getPublicationType()));
        publication.setAuthor(author);
    }

    public PublicationDTO mapToDTO(Publication publication) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setPublicationId(publication.getId());
        publicationDTO.setAuthor(userDTOMapper.mapToView(publication.getAuthor()));
        publicationDTO.setName(publication.getName());
        publicationDTO.setPublicationType(publication.getPublicationType().getType());
        publicationDTO.setProgressStatus(publication.getProgressStatus().getStatus());
        publicationDTO.setProgressPercent(publication.getProgressPercent());
        publicationDTO.setRejectionReason(publication.getRejectionReason());
        publicationDTO.setIsbn((publication.getIsbn()));
        publicationDTO.setPageNumber(publication.getPageNumber());
        publicationDTO.setLanguage(publication.getLanguage().getLanguage());
        publicationDTO.setGenre(publication.getGenre().getGenre());
        publicationDTO.setPrice(publication.getPrice());
        publicationDTO.setPublishDate(publication.getPublishDate());
        if (publication.getManager() != null) {
            publicationDTO.setManager(userDTOMapper.mapToView(publication.getManager()));
        }
        publicationDTO.setAttachments(publication.getAttachments().stream()
                .map(attachment -> attachmentDTOMapper.mapToDTO(attachment))
                .collect(Collectors.toList()));
        if (publication.getContract() != null) {
            publicationDTO.setContract(contractDTOMapper.mapToDTO(publication.getContract()));
        }
        if (publication.getPublishingBudget() != null) {
            publicationDTO.setBudget(budgetDTOMapper.mapToDTO(publication.getPublishingBudget()));
        }
        publicationDTO.setTasks(publication.getTasks().stream()
                .map(task -> taskDTOMapper.mapToDTO(task))
                .collect(Collectors.toList()));
        return publicationDTO;
    }
}
