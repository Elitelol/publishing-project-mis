package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class ContractDTO {

    private Long publicationId;
    private Long contractId;
    private Long payment;
    private Long advancedPayment;
    private boolean appliesAfterPublishing;
    private boolean ownedByPublisher;
    private String agreements;
}
