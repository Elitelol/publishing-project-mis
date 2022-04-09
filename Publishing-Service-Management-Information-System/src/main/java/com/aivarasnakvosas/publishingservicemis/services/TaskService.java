package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.Comment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.mappers.CommentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.TaskDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.CommentRepository;
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
    private CommentRepository commentRepository;
    @Autowired
    private TaskDTOMapper taskDTOMapper;
    @Autowired
    private CommentDTOMapper commentDTOMapper;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private UserService userService;

    public TaskDTO saveTask(TaskDTO taskDTO) {
        Publication publication = publicationService.getPublication(taskDTO.getPublicationId());
        List<User> responsiblePeople = userService.getUsers(taskDTO.getResponsiblePeopleIds());
        Optional<Task> existingTask = taskRepository.findById(taskDTO.getTaskId());
        Task task = existingTask.orElseGet(Task::new);
        task = taskDTOMapper.mapToTask(task, taskDTO, publication, responsiblePeople);
        task = taskRepository.save(task);
        return taskDTOMapper.mapToDTO(task);
    }

    public TaskDTO getTaskDTO(Long taskId) {
        Task task = getTask(taskId);
        return taskDTOMapper.mapToDTO(task);
    }

    public Task getTask(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()){
            throw new RuntimeException();
        }
        return task.get();
    }

    public TaskDTO addComment(CommentDTO commentDTO) {
        Task task = getTask(commentDTO.getTaskId());
        User user = userService.getUser(commentDTO.getUserId());
        Comment rootComment = null;
        if (commentDTO.getRootCommentId() != null) {
            rootComment = commentRepository.getById(commentDTO.getRootCommentId());
        }
        Comment comment = commentDTOMapper.mapToComment(commentDTO, task, user, rootComment);
        task.addComment(comment);
        task = taskRepository.save(task);
        return taskDTOMapper.mapToDTO(task);
    }
}
