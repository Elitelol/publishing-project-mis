package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.BudgetComment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.mappers.BudgetDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.CommentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.BudgetCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        PublishingBudget publishingBudget = budgetDTO.getBudgetId() != null ? findBudget(budgetDTO.getBudgetId()) : new PublishingBudget();
        budgetDTOMapper.mapToPublishingBudget(publishingBudget, budgetDTO, publication);
        budgetRepository.save(publishingBudget);
        return budgetDTOMapper.mapToDTO(publishingBudget);
    }

    public BudgetDTO addComment(CommentDTO commentDTO) {
        PublishingBudget publishingBudget = findBudget(commentDTO.getEntityId());
        User user = userService.getUser(commentDTO.getUserId());
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

    private PublishingBudget findBudget(Long id) {
        Optional<PublishingBudget> budget = budgetRepository.findById(id);
        if (budget.isEmpty()) {
            throw new RuntimeException();
        }
        return budget.get();
    }
}
