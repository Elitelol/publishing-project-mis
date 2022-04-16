package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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

    @JoinColumn(name = "ParentComment_Id", referencedColumnName = "Comment_Id")
    @ManyToOne
    private BudgetComment rootComment;

    @OneToMany(mappedBy = "rootComment", orphanRemoval = true)
    private List<BudgetComment> reply = new ArrayList<>();
}
