package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private Long numberOfCopies;
    private BigDecimal copyEditingRate;
    private BigDecimal proofReadingRate;
    private BigDecimal purchaseOfPhotosRate;
    private Long purchaseOfPhotosQuantity;
    private BigDecimal coverDesignRate;
    private Long coverDesignQuantity;
    private BigDecimal interiorLayoutRate;
    private BigDecimal printingRate;
    private BigDecimal colourPrintingRate;
    private BigDecimal deliveryToStorageRate;
    private BigDecimal advertisingCost;
    private BigDecimal copyMailingCost;
    private List<CommentDTO> comments = new ArrayList<>();
}
