package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private Long printingCost;
    private Long royaltyPerCopy;
    private Long layoutCost;
    private Long designCost;
    private List<CommentDTO> comments = new ArrayList<>();
}
