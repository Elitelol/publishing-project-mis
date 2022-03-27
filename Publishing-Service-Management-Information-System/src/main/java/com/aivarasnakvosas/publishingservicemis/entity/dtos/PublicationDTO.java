package com.aivarasnakvosas.publishingservicemis.entity.dtos;

import com.aivarasnakvosas.publishingservicemis.entity.utilities.PublicationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class PublicationDTO {

    private Long userId;
    private String name;
    private String publicationType;
}
