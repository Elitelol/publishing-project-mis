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
public class PublicationAcceptanceDTO {

    private Long managerId;
    private Long publicationId;
    private String status;
}
