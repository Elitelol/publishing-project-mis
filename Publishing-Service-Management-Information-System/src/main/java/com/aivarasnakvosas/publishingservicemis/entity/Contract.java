package com.aivarasnakvosas.publishingservicemis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "Contract_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class Contract extends AbstractBasicEntity {

    @OneToOne(mappedBy = "contract")
    private Publication publication;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractComment> comments = new ArrayList<>();

    private Long payment;
    private Long advancedPayment;
    private boolean appliesAfterPublishing;
    private boolean ownedByPublisher;
    private String agreements;
}
