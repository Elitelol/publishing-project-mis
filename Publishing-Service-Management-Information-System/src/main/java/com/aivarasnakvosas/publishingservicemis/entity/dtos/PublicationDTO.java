package com.aivarasnakvosas.publishingservicemis.entity.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class PublicationDTO {

    private Long publicationId;
    private Long userId;
    private String name;
    private String publicationType;
    private List<AttachmentDTO> attachments;
}
