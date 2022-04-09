package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Aivaras Nakvosas
 */
@Getter
@Setter
public class AttachmentDTO {

    private Long publicationId;
    private String fileName;
    private String attachmentType;
    private String contentType;
}
