package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.mappers.TaskDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskDTOMapper taskDTOMapper;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private UserService userService;

    public Task saveTask(TaskDTO taskDTO) {
        Publication publication = publicationService.getPublication(taskDTO.getPublicationId());
        List<User> responsiblePeople = userService.getResponsiblePeople(taskDTO.getResponsiblePeopleIds());
        Optional<Task> existingTask = taskRepository.findById(taskDTO.getTaskId());
        Task task = existingTask.orElseGet(Task::new);
        task = taskDTOMapper.mapToTask(task, taskDTO, publication, responsiblePeople);
        return taskRepository.save(task);
    }

    public Task getTask(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()){
            throw new RuntimeException();
        }
        return task.get();
    }
}
