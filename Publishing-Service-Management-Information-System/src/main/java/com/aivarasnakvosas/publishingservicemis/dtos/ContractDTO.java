package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class ContractDTO {

    private Long publicationId;
    private Long contractId;
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
    private List<CommentDTO> comments = new ArrayList<>();
}
