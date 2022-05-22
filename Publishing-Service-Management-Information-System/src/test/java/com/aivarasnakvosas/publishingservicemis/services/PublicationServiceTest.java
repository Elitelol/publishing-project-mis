package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.UserView;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Genre;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Language;
import com.aivarasnakvosas.publishingservicemis.entity.enums.PublicationType;
import com.aivarasnakvosas.publishingservicemis.exceptions.BusinessErrorException;
import com.aivarasnakvosas.publishingservicemis.mappers.PublicationDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskRepository;
import com.aivarasnakvosas.publishingservicemis.utils.EntityGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Aivaras Nakvosas
 */
@ExtendWith(MockitoExtension.class)
class PublicationServiceTest {

    @InjectMocks
    private PublicationService publicationService;
    @Mock
    private PublicationRepository publicationRepository;
    @Mock
    private UserService userService;
    @Mock
    private PublicationDTOMapper publicationDTOMapper;
    @Mock
    private TaskRepository taskRepository;

    @Test
    void testSaveWhenPublicationWithSelectedGenreAndLanguage() {
        UserView userView = new UserView();
        userView.setId(1L);
        PublicationDTO publicationDTO = EntityGenerator.createPublicationDTO(1L, userView, "Book", "Detective", "English");
        Publication publication = EntityGenerator.createPublication(1L, PublicationType.BOOK, Genre.DETECTIVE, Language.ENGLISH);
        User author = new User();

        when(userService.findUser(1L)).thenReturn(author);
        when(publicationRepository.findPublicationById(1L)).thenReturn(Optional.of(publication));
        doCallRealMethod().when(publicationDTOMapper).mapToPublication(publication, publicationDTO, author);
        when(publicationDTOMapper.mapToDTO(publication)).thenReturn(publicationDTO);

        PublicationDTO result = publicationService.savePublication(publicationDTO);

        assertEquals("Detective", result.getGenre());
        assertEquals("English", result.getLanguage());
    }

    @Test
    void testSubmitWhenPublicationDoesntHaveManuscript() {
        Publication publication = EntityGenerator.createPublication(1L, PublicationType.BOOK, Genre.DETECTIVE, Language.ENGLISH);
        when(publicationRepository.findPublicationById(1L)).thenReturn(Optional.of(publication));
        assertThrows(BusinessErrorException.class, () -> publicationService.submitPublication(1L));
    }

    @Test
    void testChangeStatusWhenPublicationAccepted() {
        UserView userView = new UserView();
        userView.setId(1L);
        PublicationAcceptanceDTO publicationAcceptanceDTO = EntityGenerator.createAcceptanceDTO(1L, 1L, "Accepted", null);
        Publication publication = EntityGenerator.createPublication(1L, PublicationType.BOOK, Genre.DETECTIVE, Language.ENGLISH);
        PublicationDTO publicationDTO = EntityGenerator.createPublicationDTO(1L, userView, "Book", "Detective", "English");
        publicationDTO.setProgressStatus("Accepted");

        when(publicationRepository.findPublicationById(1L)).thenReturn(Optional.of(publication));
        when(publicationDTOMapper.mapToDTO(publication)).thenReturn(publicationDTO);

        PublicationDTO result = publicationService.changePublicationStatus(publicationAcceptanceDTO);

        assertEquals("Accepted", result.getProgressStatus());
    }

    @Test
    void testGetPublications() {
        Publication publication1 = EntityGenerator.createPublication(1L, PublicationType.BOOK, Genre.DETECTIVE, Language.ENGLISH);
        Publication publication2 = EntityGenerator.createPublication(2L, PublicationType.BOOK, Genre.DETECTIVE, Language.ENGLISH);
        List<Publication> publications = Arrays.asList(publication1, publication2);
        PublicationDTO publicationDTO1 = EntityGenerator.createPublicationDTO(1L, new UserView(), "Book", "Detective", "English");

        when(publicationRepository.findAll()).thenReturn(publications);
        when(publicationDTOMapper.mapToDTO(any())).thenReturn(publicationDTO1);

        List<PublicationDTO> result = publicationService.getPublications();
        assertEquals(2, result.size());
    }

    @Test
    void testSetCompleteWhenEverythingIsDone() {
        Publication publication = EntityGenerator.createPublication(1L, PublicationType.BOOK, Genre.DETECTIVE, Language.ENGLISH, true, new PublishingBudget());
        PublicationDTO publicationDTO = EntityGenerator.createPublicationDTO(1L, new UserView(), "Book", "Detective", "English", "Completed");

        when(publicationRepository.findPublicationById(1L)).thenReturn(Optional.of(publication));
        when(publicationDTOMapper.mapToDTO(publication)).thenReturn(publicationDTO);

        PublicationDTO result = publicationService.setReadyForPublish(1L);

        assertEquals("Completed", result.getProgressStatus());
    }
}
