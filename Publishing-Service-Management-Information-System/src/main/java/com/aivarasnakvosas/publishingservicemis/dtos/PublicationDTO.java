package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class PublicationDTO {

    private Long publicationId;
    private List<Long> authorId;

    private String name;
    private String isbn;
    private Long pageNumber;
    private String language;
    private String genre;
    private Long price;
    private Date publishDate;
    private String publicationType;
    private List<AttachmentDTO> attachments;
}
