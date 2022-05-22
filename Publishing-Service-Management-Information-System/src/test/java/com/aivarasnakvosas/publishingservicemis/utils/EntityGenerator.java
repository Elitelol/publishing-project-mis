package com.aivarasnakvosas.publishingservicemis.utils;

import com.aivarasnakvosas.publishingservicemis.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.UserView;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Genre;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Language;
import com.aivarasnakvosas.publishingservicemis.entity.enums.PublicationType;

import java.util.Date;

/**
 * @author Aivaras Nakvosas
 */
public class EntityGenerator {

    public static Publication createPublication(Long id, PublicationType type, Genre genre, Language language) {
        Publication publication = new Publication();
        publication.setId(id);
        publication.setPublicationType(type);
        publication.setGenre(genre);
        publication.setLanguage(language);
        return publication;
    }

    public static Publication createPublication(Long id, PublicationType type, Genre genre, Language language, boolean signed, PublishingBudget publishingBudget) {
        Publication publication = new Publication();
        publication.setId(id);
        publication.setPublicationType(type);
        publication.setGenre(genre);
        publication.setLanguage(language);
        publication.setContractSigned(signed);
        publication.setPublishingBudget(publishingBudget);
        return publication;
    }

    public static PublicationDTO createPublicationDTO(Long id, UserView author, String type, String genre, String language) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setPublicationId(id);
        publicationDTO.setAuthor(author);
        publicationDTO.setPublicationType(type);
        publicationDTO.setGenre(genre);
        publicationDTO.setLanguage(language);
        return publicationDTO;
    }

    public static PublicationDTO createPublicationDTO(Long id, UserView author, String type, String genre, String language, String status) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setPublicationId(id);
        publicationDTO.setAuthor(author);
        publicationDTO.setPublicationType(type);
        publicationDTO.setGenre(genre);
        publicationDTO.setLanguage(language);
        publicationDTO.setProgressStatus(status);
        return publicationDTO;
    }

    public static PublicationAcceptanceDTO createAcceptanceDTO(Long managerId, Long publicationId, String status, String rejectionReason) {
        PublicationAcceptanceDTO publicationAcceptanceDTO = new PublicationAcceptanceDTO();
        publicationAcceptanceDTO.setManagerId(managerId);
        publicationAcceptanceDTO.setPublicationId(publicationId);
        publicationAcceptanceDTO.setStatus(status);
        publicationAcceptanceDTO.setRejectionReason(rejectionReason);
        return publicationAcceptanceDTO;
    }

    public static TaskDTO createTaskDTO(Date start, Date due) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStartDate(start);
        taskDTO.setDueDate(due);
        return taskDTO;
    }

    public static UserDTO createUserDTO(Long id, String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(username);
        return userDTO;
    }

    public static UserView createUserView(Long id, String username) {
        UserView userView = new UserView();
        userView.setId(id);
        userView.setUsername(username);
        return userView;
    }

    public static User createUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
}
