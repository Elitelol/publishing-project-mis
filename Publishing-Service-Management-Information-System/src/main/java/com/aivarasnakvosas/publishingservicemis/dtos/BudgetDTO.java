package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class BudgetDTO {

    private Long publicationId;
    private Long budgetId;
    private Long pagePrice;
    private Long printingPCost;
    private Long royaltyPerCopy;
    private Long layoutCost;
    private Long designCost;
}
