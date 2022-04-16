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
public class ContractComment extends Comment {

    @JoinColumn(name = "Contract_Id", referencedColumnName = "Contract_Id")
    @ManyToOne
    private Contract contract;
}
