package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.BudgetComment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import com.aivarasnakvosas.publishingservicemis.mappers.BudgetDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.CommentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.BudgetCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Service
@Transactional
public class PublishingBudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private BudgetCommentRepository budgetCommentRepository;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private BudgetDTOMapper budgetDTOMapper;
    @Autowired
    private CommentDTOMapper commentDTOMapper;

    public BudgetDTO savePublishingBudget(BudgetDTO budgetDTO) {
        Publication publication = publicationService.findPublication(budgetDTO.getPublicationId());
        if (ProgressStatus.ACCEPTED.equals(publication.getProgressStatus())) {
            publication.setProgressStatus(ProgressStatus.IN_PROGRESS);
        }
        PublishingBudget publishingBudget = budgetDTO.getBudgetId() != null ? findBudget(budgetDTO.getBudgetId()) : new PublishingBudget();
        budgetDTOMapper.mapToPublishingBudget(publishingBudget, budgetDTO, publication);
        budgetRepository.save(publishingBudget);
        return budgetDTOMapper.mapToDTO(publishingBudget);
    }

    public BudgetDTO addComment(CommentDTO commentDTO) {
        PublishingBudget publishingBudget = findBudget(commentDTO.getEntityId());
        User user = userService.findUser(commentDTO.getUser().getId());
        BudgetComment rootComment = null;
        if (commentDTO.getRootCommentId() != null) {
            rootComment = budgetCommentRepository.getById(commentDTO.getRootCommentId());
        }
        BudgetComment budgetComment = commentDTOMapper.mapToBudgetComment(commentDTO, publishingBudget, user, rootComment);
        publishingBudget.addComment(budgetComment);
        budgetRepository.save(publishingBudget);
        return budgetDTOMapper.mapToDTO(publishingBudget);
    }

    public BudgetDTO getPublishingBudget(Long id) {
        PublishingBudget publishingBudget = findBudget(id);
        return budgetDTOMapper.mapToDTO(publishingBudget);
    }

    public Map<String, Object> getBudgetDataForPDF(Long id) {
        Map<String, Object> publishingData = new HashMap<>();
        PublishingBudget publishingBudget = findBudget(id);
        Publication publication = publishingBudget.getPublication();

        StringBuilder authorNames = new StringBuilder();
        publication.getAuthors()
                .iterator()
                .forEachRemaining(user -> authorNames.append(user.getFirstName()).append(" ").append(user.getLastName()).append(", "));
        authorNames.setLength(authorNames.length() - 2);
        publication.setAuthorsName(authorNames.toString());

        BigDecimal numberOfPages = BigDecimal.valueOf(publication.getPageNumber());
        BigDecimal numberOfCopies = BigDecimal.valueOf(publishingBudget.getNumberOfCopies());
        publishingBudget.setCopyEditingCost(publishingBudget.getCopyEditingRate().multiply(numberOfPages));
        publishingBudget.setProofReadingCost(publishingBudget.getProofReadingRate().multiply(numberOfPages));
        publishingBudget.setPurchaseOfPhotosCost(publishingBudget.getPurchaseOfPhotosRate().multiply(BigDecimal.valueOf(publishingBudget.getPurchaseOfPhotosQuantity())));
        publishingBudget.setCoverDesignCost(publishingBudget.getCoverDesignRate().multiply(BigDecimal.valueOf(publishingBudget.getCoverDesignQuantity())));
        publishingBudget.setInteriorDesignCost(publishingBudget.getInteriorLayoutRate().multiply(numberOfPages));
        publishingBudget.setPrintingCost(publishingBudget.getPrintingRate().multiply(numberOfCopies));
        publishingBudget.setColourPrintingCost(numberOfPages.multiply(publishingBudget.getColourPrintingRate()));
        publishingBudget.setDeliveryToStorageCost(publishingBudget.getDeliveryToStorageRate().multiply(numberOfCopies));
        BigDecimal totalEditorialCost = publishingBudget.getCopyEditingCost()
                .add(publishingBudget.getProofReadingCost())
                .add(publishingBudget.getPurchaseOfPhotosCost())
                .add(publishingBudget.getCoverDesignCost())
                .add(publishingBudget.getInteriorDesignCost());
        BigDecimal totalPrintingCost = publishingBudget.getPrintingCost()
                .add(publishingBudget.getColourPrintingCost())
                .add(publishingBudget.getDeliveryToStorageCost());
        BigDecimal totalCommunicationsCost = publishingBudget.getAdvertisingCost().add(publishingBudget.getCopyMailingCost());
        BigDecimal totalPublishingCost = totalEditorialCost.add(totalPrintingCost).add(totalCommunicationsCost);
        publishingBudget.setTotalEditorialCost(totalEditorialCost);
        publishingBudget.setTotalPrintingCost(totalPrintingCost);
        publishingBudget.setTotalCommunicationCost(totalCommunicationsCost);
        publishingBudget.setTotalPublishingCost(totalPublishingCost);
        publishingData.put("publication", publication);
        publishingData.put("budget", publishingBudget);
        return publishingData;
    }

    private PublishingBudget findBudget(Long id) {
        Optional<PublishingBudget> budget = budgetRepository.findById(id);
        if (budget.isEmpty()) {
            throw new EntityNotFoundException(PublishingBudget.class);
        }
        return budget.get();
    }

    public void deleteBudgetComment(Long id) {
        BudgetComment budgetComment = findBudgetComment(id);
        budgetCommentRepository.delete(budgetComment);
    }

    private BudgetComment findBudgetComment(Long id) {
        Optional<BudgetComment> budgetComment = budgetCommentRepository.findById(id);
        if (budgetComment.isEmpty()) {
            throw new EntityNotFoundException(BudgetComment.class);
        }
        return budgetComment.get();
    }
}
