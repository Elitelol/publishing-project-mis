package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.exceptions.InvalidDataException;
import com.aivarasnakvosas.publishingservicemis.mappers.BudgetDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.ContractDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.PublicationDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.BudgetRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.ContractRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public PublicationDTO getPublication(Long id) {
        Publication publication = findPublication(id);
        return publicationDTOMapper.mapToDTO(publication);
    }

    public Publication findPublication(Long id) {
        Optional<Publication> publication = publicationRepository.findPublicationById(id);
        if (publication.isEmpty()) {
            throw new RuntimeException();
        }
        return publication.get();
    }

    public PublicationDTO savePublication(PublicationDTO publicationDTO) {
        List<User> author = userService.getUsers(publicationDTO.getAuthorId());
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
            throw new InvalidDataException("Publication doesn't have manuscript.");
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
            throw new RuntimeException();
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.REJECTED.name())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.IN_REVIEW) || publicationAcceptanceDTO.getRejectionReason() == null) {
                throw new RuntimeException();
            }
            publication.setRejectionReason(publicationAcceptanceDTO.getRejectionReason());
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.ACCEPTED.name())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.IN_REVIEW)) {
                throw new RuntimeException();
            }
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.IN_PROGRESS.name())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.ACCEPTED)) {
                throw new RuntimeException();
            }
        }
        if (publicationAcceptanceDTO.getStatus().equals(ProgressStatus.PUBLISHED.name())) {
            if (!publication.getProgressStatus().equals(ProgressStatus.COMPLETED)) {
                throw new RuntimeException();
            }
        }
        publication.setProgressStatus(ProgressStatus.valueOf(publicationAcceptanceDTO.getStatus()));
        publication.setDateModified(new Date());
        publicationRepository.save(publication);
    }

    public PublicationDTO addManager(Long publicationId, Long managerId) {
        User manager = userService.getUser(managerId);
        Publication publication = findPublication(publicationId);
        if (publication.getManager() != null) {
            throw new RuntimeException();
        }
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
            throw new RuntimeException();
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
            throw new RuntimeException();
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
        List<Publication> publications = publicationRepository.findPublicationsByAuthorsId(authorId);
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
        ProgressStatus progressStatus = ProgressStatus.valueOf(status);
        List<Publication> publications = publicationRepository.findPublicationsByProgressStatus(progressStatus);
        return getPublicationDTOS(publications);
    }

    private List<PublicationDTO> getPublicationDTOS(List<Publication> publications) {
        return publications.stream()
                .map(publication -> publicationDTOMapper.mapToDTO(publication))
                .collect(Collectors.toList());
    }
}
