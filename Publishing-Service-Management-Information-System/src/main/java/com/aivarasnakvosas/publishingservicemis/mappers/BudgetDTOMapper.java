package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class BudgetDTOMapper {

    @Autowired
    private CommentDTOMapper commentDTOMapper;

    public void mapToPublishingBudget(PublishingBudget publishingBudget, BudgetDTO budgetDTO, Publication publication){
        publishingBudget.setPagePrice(budgetDTO.getPagePrice());
        publishingBudget.setPrintingCost(budgetDTO.getPrintingCost());
        publishingBudget.setRoyaltyPerCopy(budgetDTO.getRoyaltyPerCopy());
        publishingBudget.setLayoutCost(budgetDTO.getLayoutCost());
        publishingBudget.setDesignCost(budgetDTO.getDesignCost());
        publishingBudget.setPublication(publication);
    }

    public BudgetDTO mapToDTO(PublishingBudget publishingBudget) {
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setPublicationId(publishingBudget.getPublication().getId());
        budgetDTO.setBudgetId(publishingBudget.getId());
        budgetDTO.setPagePrice(publishingBudget.getPagePrice());
        budgetDTO.setPrintingCost(publishingBudget.getPrintingCost());
        budgetDTO.setRoyaltyPerCopy(publishingBudget.getRoyaltyPerCopy());
        budgetDTO.setLayoutCost(publishingBudget.getLayoutCost());
        budgetDTO.setDesignCost(publishingBudget.getDesignCost());
        budgetDTO.setComments(publishingBudget.getComments().stream()
                .filter(comment -> comment.getRootComment() == null)
                .map(budgetComment -> commentDTOMapper.mapToDTO(budgetComment))
                .collect(Collectors.toList()));
        return budgetDTO;
    }
}
