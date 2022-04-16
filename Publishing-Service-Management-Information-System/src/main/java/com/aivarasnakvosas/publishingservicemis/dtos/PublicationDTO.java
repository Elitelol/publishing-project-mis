package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    private List<Long> authorId = new ArrayList<>();

    private String name;
    private String publicationType;
    private String progressStatus;
    private String rejectionReason;
    private String isbn;
    private Long pageNumber;
    private String language;
    private String genre;
    private Long price;
    private Date publishDate;
    private Long managerId;
    private List<AttachmentDTO> attachments = new ArrayList<>();
    private ContractDTO contract;
    private BudgetDTO budget;
    private List<TaskDTO> tasks = new ArrayList<>();
}
