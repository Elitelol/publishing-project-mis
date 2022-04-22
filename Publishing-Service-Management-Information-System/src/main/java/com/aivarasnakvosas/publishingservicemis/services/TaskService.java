package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.TaskComment;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.exceptions.BusinessErrorException;
import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import com.aivarasnakvosas.publishingservicemis.mappers.CommentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.TaskDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskCommentRepository taskCommentRepository;
    @Autowired
    private TaskDTOMapper taskDTOMapper;
    @Autowired
    private CommentDTOMapper commentDTOMapper;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private UserService userService;

    public TaskDTO saveTask(TaskDTO taskDTO) {
        Publication publication = publicationService.findPublication(taskDTO.getPublicationId());
        List<User> responsiblePeople = userService.findUsers(taskDTO.getResponsiblePeopleIds());
        Task task = taskDTO.getTaskId() != null ? findTask(taskDTO.getTaskId()) : new Task();
        taskDTOMapper.mapToTask(task, taskDTO, publication, responsiblePeople);
        taskRepository.save(task);
        return taskDTOMapper.mapToDTO(task);
    }

    public TaskDTO getTask(Long taskId) {
        Task task = findTask(taskId);
        return taskDTOMapper.mapToDTO(task);
    }

    private Task findTask(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()){
            throw new EntityNotFoundException(Task.class);
        }
        return task.get();
    }

    public TaskDTO addComment(CommentDTO commentDTO) {
        Task task = findTask(commentDTO.getEntityId());
        User user = userService.findUser(commentDTO.getUserId());
        TaskComment rootComment = null;
        if (commentDTO.getRootCommentId() != null) {
            rootComment = taskCommentRepository.getById(commentDTO.getRootCommentId());
        }
        TaskComment comment = commentDTOMapper.mapToTaskComment(commentDTO, task, user, rootComment);
        task.addComment(comment);
        taskRepository.save(task);
        return taskDTOMapper.mapToDTO(task);
    }

    public void deleteTask(Long id) {
        Task task = findTask(id);
        if (task.getProgressStatus().equals(ProgressStatus.COMPLETED)) {
            throw new BusinessErrorException(String.format("Completed task %d can't be deleted.", task.getId()));
        }
        taskRepository.delete(task);
    }

    public void deleteTaskComment(Long taskId) {
        TaskComment taskComment = findTaskComment(taskId);
        taskCommentRepository.delete(taskComment);
    }

    private TaskComment findTaskComment(Long id) {
        Optional<TaskComment> taskComment = taskCommentRepository.findById(id);
        if (taskComment.isEmpty()) {
            throw new EntityNotFoundException(TaskComment.class);
        }
        return taskComment.get();
    }
}
