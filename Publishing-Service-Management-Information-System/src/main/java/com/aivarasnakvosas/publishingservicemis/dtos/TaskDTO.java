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
public class TaskDTO {

    private Long taskId;
    private Long publicationId;
    private List<Long> responsiblePeopleIds;
    private String taskType;
    private String taskName;
    private String description;
    private Date startDate;
    private Date dueDate;

}
