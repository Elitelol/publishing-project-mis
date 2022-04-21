package com.aivarasnakvosas.publishingservicemis.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    @JoinColumn(name = "Publication_Id", referencedColumnName = "Publication_Id")
    @OneToOne(cascade = CascadeType.ALL)
    private Publication publication;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractComment> comments = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    private BigDecimal publicationPrice;
    private BigDecimal amountOnSigningContract;
    private BigDecimal amountOfCompletedManuscript;
    private BigDecimal amountOnInitialPublish;
    private Long withinMonthsAfterPublish;
    private Long firstCoverRate;
    private BigDecimal firstCoverPercent;
    private Long secondCoverRate;
    private BigDecimal secondCoverPercent;
    private Long lastCoverRate;
    private BigDecimal lastCoverPercent;

    public void addComment(ContractComment contractComment) {
        comments.add(contractComment);
    }
}
