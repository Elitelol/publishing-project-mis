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
public class ContractComment extends Comment {

    @JoinColumn(name = "Contract_Id", referencedColumnName = "Contract_Id")
    @ManyToOne
    private Contract contract;

    @JoinColumn(name = "ParentComment_Id", referencedColumnName = "Comment_Id")
    @ManyToOne
    private ContractComment rootComment;

    @OneToMany(mappedBy = "rootComment", orphanRemoval = true)
    private List<ContractComment> reply = new ArrayList<>();
}
