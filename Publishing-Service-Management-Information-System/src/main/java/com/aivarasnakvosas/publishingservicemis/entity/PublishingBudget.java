package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "Budget_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class PublishingBudget extends AbstractBasicEntity {

    @OneToOne(mappedBy = "publishingBudget")
    private Publication publication;

    private Long pagePrice;
    private Long printingCost;
    private Long royaltyPerCopy;
    private Long layoutCost;
    private Long designCost;

}
