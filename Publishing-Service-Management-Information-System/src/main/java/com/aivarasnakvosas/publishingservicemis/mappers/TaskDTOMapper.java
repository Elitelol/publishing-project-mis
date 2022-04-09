package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.TaskType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class TaskDTOMapper {

    public Task mapToTask(Task task, TaskDTO taskDTO, Publication publication, List<User> responsiblePeople) {
        task.setPublication(publication);
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskType(TaskType.valueOf(taskDTO.getTaskType()));
        task.setDescription(taskDTO.getDescription());
        task.setStartDate(taskDTO.getStartDate());
        task.setDueDate(taskDTO.getDueDate());
        task.setResponsiblePeople(responsiblePeople);
        return task;
    }
}
