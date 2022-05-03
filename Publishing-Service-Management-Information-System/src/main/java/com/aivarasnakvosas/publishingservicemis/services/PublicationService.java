package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.exceptions.BusinessErrorException;
import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import com.aivarasnakvosas.publishingservicemis.mappers.PublicationDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private TaskRepository taskRepository;

    public PublicationDTO getPublication(Long id) {
        Publication publication = findPublication(id);
        return publicationDTOMapper.mapToDTO(publication);
    }

    public Publication findPublication(Long id) {
        Optional<Publication> publication = publicationRepository.findPublicationById(id);
        if (publication.isEmpty()) {
            throw new EntityNotFoundException(Publication.class);
        }
        return publication.get();
    }

    public PublicationDTO savePublication(PublicationDTO publicationDTO) {
        List<User> author = userService.findUsers(publicationDTO.getAuthorId());
        Optional<Publication> existingPublication = publicationRepository.findPublicationById(publicationDTO.getPublicationId());
        Publication publication = existingPublication.orElseGet(Publication::new);
        publicationDTOMapper.mapToPublication(publication, publicationDTO, author);
        publicationRepository.save(publication);
        return publicationDTOMapper.mapToDTO(publication);
    }

    public PublicationDTO submitPublication(Long id) {
        Publication publication = findPublication(id);
        boolean manuscriptUploaded = publication.getAttachments().stream()
                .anyMatch(attachment -> attachment.getAttachmentType().equals(AttachmentType.MANUSCRIPT));
        if (manuscriptUploaded) {
            publication.setProgressStatus(ProgressStatus.NOT_STARTED);
        } else {
            throw new BusinessErrorException(String.format("Publication %d doesn't have manuscript.", publication.getId()));
        }
        publicationRepository.save(publication);
        return publicationDTOMapper.mapToDTO(publication);
    }

    public PublicationDTO changePublicationStatus(PublicationAcceptanceDTO publicationAcceptanceDTO) {
        Publication publication = findPublication(publicationAcceptanceDTO.getPublicationId());
        setPublicationStatus(publication, publicationAcceptanceDTO);
        return publicationDTOMapper.mapToDTO(publication);
    }

    private void setPublicationStatus(Publication publication, PublicationAcceptanceDTO publicationAcceptanceDTO) {
        if (!publication.getManager().getId().equals(publicationAcceptanceDTO.getManagerId())) {
            throw new BusinessErrorException(String.format("Publication %d assigned manager isn't valid.", publication.getId()));
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.REJECTED.getStatus())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.IN_REVIEW) || publicationAcceptanceDTO.getRejectionReason() == null) {
                throw new BusinessErrorException(String.format("Incorrect Publication status change for %s to %s", publication.getProgressStatus().name(), publicationAcceptanceDTO.getStatus()));
            }
            publication.setRejectionReason(publicationAcceptanceDTO.getRejectionReason());
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.ACCEPTED.getStatus())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.IN_REVIEW)) {
                throw new BusinessErrorException(String.format("Incorrect Publication status change for %s to %s", publication.getProgressStatus().name(), publicationAcceptanceDTO.getStatus()));
            }
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.IN_PROGRESS.getStatus())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.ACCEPTED)) {
                throw new BusinessErrorException(String.format("Incorrect Publication status change for %s to %s", publication.getProgressStatus().name(), publicationAcceptanceDTO.getStatus()));
            }
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.PUBLISHED.getStatus())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.COMPLETED)) {
                throw new BusinessErrorException(String.format("Incorrect Publication status change for %s to %s", publication.getProgressStatus().name(), publicationAcceptanceDTO.getStatus()));
            }
        }
        publication.setProgressStatus(ProgressStatus.getStatus(publicationAcceptanceDTO.getStatus()));
        publication.setDateModified(new Date());
        publicationRepository.save(publication);
    }

    public PublicationDTO addManager(Long publicationId, Long managerId) {
        User manager = userService.findUser(managerId);
        Publication publication = findPublication(publicationId);
        publication.setManager(manager);
        publication.setProgressStatus(ProgressStatus.IN_REVIEW);
        publication.setDateModified(new Date());
        publicationRepository.save(publication);
        return publicationDTOMapper.mapToDTO(publication);
    }

    public PublicationDTO setContractSigned(Long publicationId) {
        Publication publication = findPublication(publicationId);
        boolean writtenContractUploaded = publication.getAttachments().stream()
                .anyMatch(attachment -> attachment.getAttachmentType().equals(AttachmentType.CONTRACT));
        boolean contractSet = publication.getContract() != null && writtenContractUploaded;
        if (contractSet) {
            publication.setContractSigned(true);
        } else {
            throw new BusinessErrorException(String.format("Publication %d doesn't have contract/contract attachment", publication.getId()));
        }
        publicationRepository.save(publication);
        return publicationDTOMapper.mapToDTO(publication);
    }

    public PublicationDTO setReadyForPublish(Long publicationId) {
        Publication publication = findPublication(publicationId);
        boolean allTaskAreDone = publication.getTasks().stream()
                .allMatch(task -> task.getProgressStatus().equals(ProgressStatus.COMPLETED));
        boolean budgetAndContractsAreSet = publication.isContractSigned() && publication.getPublishingBudget() != null;
        if (allTaskAreDone && budgetAndContractsAreSet) {
            publication.setProgressStatus(ProgressStatus.COMPLETED);
        } else {
            throw new BusinessErrorException(String.format("Publication %d has unfinished tasks or contract and budget are not set.", publication.getId()));
        }
        publication.setDateModified(new Date());
        publicationRepository.save(publication);
        return publicationDTOMapper.mapToDTO(publication);
    }

    public List<PublicationDTO> getPublications() {
        List<Publication> publications = publicationRepository.findAll();
        return getPublicationDTOS(publications);
    }

    public List<PublicationDTO> getAuthorPublications(Long authorId) {
        Set<User> authors = new HashSet<>(Collections.singletonList(userService.findUser(authorId)));
        List<Publication> publications = publicationRepository.findPublicationsByAuthorsIn(authors);
        return getPublicationDTOS(publications);
    }

    public List<PublicationDTO> getManagerPublications(Long managerId) {
        List<Publication> publications = publicationRepository.findPublicationsByManagerId(managerId);
        return getPublicationDTOS(publications);
    }

    public List<PublicationDTO> getUnmanagedPublications() {
        List<Publication> publications = publicationRepository.findUnmanagedPublications();
        return getPublicationDTOS(publications);
    }

    public List<PublicationDTO> getPublicationByStatus(String status) {
        ProgressStatus progressStatus = ProgressStatus.getStatus(status);
        List<Publication> publications = publicationRepository.findPublicationsByProgressStatus(progressStatus);
        return getPublicationDTOS(publications);
    }

    public List<PublicationDTO> getPublicationsByResponsiblePeople(Long userId) {
        List<User> userList = Collections.singletonList(userService.findUser(userId));
        List<Task> userTasks = taskRepository.findTaskByResponsiblePeopleIn(userList);
        List<Publication> publications = userTasks.stream()
                .map(Task::getPublication)
                .collect(Collectors.toList());
        return getPublicationDTOS(publications);
    }

    private List<PublicationDTO> getPublicationDTOS(List<Publication> publications) {
        return publications.stream()
                .map(publication -> publicationDTOMapper.mapToDTO(publication))
                .collect(Collectors.toList());
    }

    public void deletePublication(Long id) {
        Publication publication = findPublication(id);
        if (!(publication.getProgressStatus().equals(ProgressStatus.NOT_SUBMITTED)
                || publication.getProgressStatus().equals(ProgressStatus.REJECTED))) {
            throw new BusinessErrorException(String.format("Deleting publication %d with status %s isn't allowed",
                    publication.getId(), publication.getProgressStatus().getStatus()));
        }
        publicationRepository.delete(publication);
    }
}
