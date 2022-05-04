package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private List<UserView> responsiblePeople;
    private String taskType;
    @NotBlank(message = "Task must have a name")
    private String taskName;
    @NotBlank(message = "Task must have a description")
    private String description;
    @NotNull(message = "Task must have a start date")
    private Date startDate;
    @NotNull(message = "Task must have a due date")
    private Date dueDate;
    private String progress;
    private List<CommentDTO> comments;
}
