package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
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

/**
 * @author Aivaras Nakvosas
 */
@Service
@Transactional
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PublicationDTOMapper publicationDTOMapper;
    @Autowired
    private ContractDTOMapper contractDTOMapper;
    @Autowired
    private BudgetDTOMapper budgetDTOMapper;
    @Autowired
    private AttachmentService attachmentService;

    public PublicationDTO getPublicationDTO(Long id) {
        Publication publication = getPublication(id);
        PublicationDTO publicationDTO = publicationDTOMapper.mapToDTO(publication);
        return publicationDTO;
    }

    public Publication getPublication(Long id) {
        Optional<Publication> publication = publicationRepository.findPublicationById(id);
        return publication.orElse(null);
    }

    public Publication getExistingPublication(Long id) {
        Optional<Publication> publication = publicationRepository.findPublicationById(id);
        if (publication.isEmpty()) {
            throw new RuntimeException();
        }
        return publication.get();
    }

    public Publication savePublication(PublicationDTO publicationDTO) {
        List<User> author = userService.getUsers(publicationDTO.getAuthorId());
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
            attachmentDTOS.forEach(attachmentDTO -> attachmentService.saveAttachment(attachmentDTO, publication));
        } else {
            throw new RuntimeException();
        }
    }

    public Publication changePublicationStatus(PublicationAcceptanceDTO publicationAcceptanceDTO) {
        Publication publication = getExistingPublication(publicationAcceptanceDTO.getPublicationId());
        return setPublicationStatus(publication, publicationAcceptanceDTO);
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

    public Publication saveContract(ContractDTO contractDTO) {
        Publication publication = getExistingPublication(contractDTO.getPublicationId());
        Optional<Contract> existingContract = contractRepository.findById(contractDTO.getContractId());
        Contract contract = existingContract.orElseGet(Contract::new);
        contract = contractDTOMapper.mapToContract(contract, contractDTO);
        publication.setContract(contract);
        return publicationRepository.save(publication);
    }

    public Publication saveBudget(BudgetDTO budgetDTO) {
        Publication publication = getExistingPublication(budgetDTO.getPublicationId());
        Optional<PublishingBudget> existingBudget = budgetRepository.findById(budgetDTO.getBudgetId());
        PublishingBudget publishingBudget = existingBudget.orElseGet(PublishingBudget::new);
        publishingBudget = budgetDTOMapper.mapToPublishingBudget(publishingBudget, budgetDTO);
        publication.setPublishingBudget(publishingBudget);
        return publicationRepository.save(publication);
    }

    public List<Publication> getUnmanagedPublications() {
        return publicationRepository.findPublicationsByManagerIdIsNull();
    }
}
