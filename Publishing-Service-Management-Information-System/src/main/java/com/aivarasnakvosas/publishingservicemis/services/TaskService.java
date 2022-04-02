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
        Task task = taskDTOMapper.mapToTask(taskDTO, publication, responsiblePeople);
        return taskRepository.save(task);
    }
}
