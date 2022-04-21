package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.AbstractBasicEntity;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.enums.TaskType;
import com.aivarasnakvosas.publishingservicemis.exceptions.BusinessErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class TaskDTOMapper {

    @Autowired
    private CommentDTOMapper commentDTOMapper;

    public void mapToTask(Task task, TaskDTO taskDTO, Publication publication, List<User> responsiblePeople) {
        task.setPublication(publication);
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskType(TaskType.valueOf(taskDTO.getTaskType()));
        task.setDescription(taskDTO.getDescription());
        Date startDate = taskDTO.getStartDate();
        Date dueDate = taskDTO.getDueDate();
        if (dueDate.before(startDate)) {
            throw new BusinessErrorException(String.format("Task due date %s can't be before start date %s", dueDate, startDate));
        }
        task.setStartDate(taskDTO.getStartDate());
        task.setDueDate(taskDTO.getDueDate());
        task.setProgressStatus(ProgressStatus.valueOf(taskDTO.getProgress()));
        task.setResponsiblePeople(responsiblePeople);
    }

    public TaskDTO mapToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getId());
        taskDTO.setPublicationId(task.getPublication().getId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setTaskType(task.getTaskType().name());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStartDate(task.getStartDate());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setProgress(task.getProgressStatus().toString());
        List<Long> users = task.getResponsiblePeople()
                .stream()
                .map(AbstractBasicEntity::getId)
                .collect(Collectors.toList());
        taskDTO.setResponsiblePeopleIds(users);
        List<CommentDTO> comments = task.getComments().stream()
                .filter(comment -> comment.getRootComment() == null)
                .map(comment -> commentDTOMapper.mapToDTO(comment))
                .collect(Collectors.toList());
        taskDTO.setComments(comments);
        return taskDTO;
    }
}
