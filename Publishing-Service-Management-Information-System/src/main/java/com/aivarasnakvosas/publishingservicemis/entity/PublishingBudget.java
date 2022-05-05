package com.aivarasnakvosas.publishingservicemis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "Budget_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class PublishingBudget extends AbstractBasicEntity {

    @JoinColumn(name = "Publication_Id", referencedColumnName = "Publication_Id")
    @OneToOne(cascade = CascadeType.ALL)
    private Publication publication;

    @OneToMany(mappedBy = "publishingBudget", cascade = CascadeType.ALL)
    private List<BudgetComment> comments = new ArrayList<>();

    private Long pageNumber;
    private Long numberOfCopies;
    // content preparation
    private BigDecimal copyEditingRate;
    private BigDecimal proofReadingRate;
    private BigDecimal purchaseOfPhotosRate;
    private Long purchaseOfPhotosQuantity;
    private BigDecimal coverDesignRate;
    private Long coverDesignQuantity;
    private BigDecimal interiorLayoutRate;
    // printing
    private BigDecimal printingRate;
    private BigDecimal colourPrintingRate;
    private BigDecimal deliveryToStorageRate;
    // communications
    private BigDecimal advertisingCost;
    private BigDecimal copyMailingCost;

    // for PDF
    // Editorial
    @Transient
    private BigDecimal copyEditingCost;
    @Transient
    private BigDecimal proofReadingCost;
    @Transient
    private BigDecimal purchaseOfPhotosCost;
    @Transient
    private BigDecimal coverDesignCost;
    @Transient
    private BigDecimal interiorDesignCost;

    //Printing
    @Transient
    private BigDecimal printingCost;
    @Transient
    private BigDecimal colourPrintingCost;
    @Transient
    private BigDecimal deliveryToStorageCost;

    @Transient
    private BigDecimal totalEditorialCost;
    @Transient
    private BigDecimal totalPrintingCost;
    @Transient
    private BigDecimal totalCommunicationCost;
    @Transient
    private BigDecimal totalPublishingCost;

    public void addComment(BudgetComment budgetComment) {
        comments.add(budgetComment);
    }
}
