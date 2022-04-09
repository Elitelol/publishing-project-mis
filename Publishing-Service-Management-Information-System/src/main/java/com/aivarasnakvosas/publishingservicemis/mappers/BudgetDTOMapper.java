package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import org.springframework.stereotype.Component;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class BudgetDTOMapper {

    public PublishingBudget mapToPublishingBudget(PublishingBudget publishingBudget, BudgetDTO budgetDTO){
        publishingBudget.setPagePrice(budgetDTO.getPagePrice());
        publishingBudget.setPrintingCost(budgetDTO.getPrintingPCost());
        publishingBudget.setRoyaltyPerCopy(budgetDTO.getRoyaltyPerCopy());
        publishingBudget.setLayoutCost(budgetDTO.getLayoutCost());
        publishingBudget.setDesignCost(budgetDTO.getDesignCost());
        return publishingBudget;
    }
}
