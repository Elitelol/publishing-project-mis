package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class BudgetComment extends Comment {

    @JoinColumn(name = "Budget_Id", referencedColumnName = "Budget_Id")
    @ManyToOne
    private PublishingBudget publishingBudget;
}
