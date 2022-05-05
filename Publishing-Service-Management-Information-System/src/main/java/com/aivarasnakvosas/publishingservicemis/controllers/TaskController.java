package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    ResponseEntity<TaskDTO> saveTask(@Valid @RequestBody TaskDTO taskDTO){
        TaskDTO task = taskService.saveTask(taskDTO);
        return ResponseEntity.ok(task);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        TaskDTO task = taskService.getTask(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping(value = "/{publicationId}/notStartedTasks")
    ResponseEntity<List<TaskDTO>> getNotStartedPublicationTasks(@PathVariable Long publicationId) {
        List<TaskDTO> tasks = taskService.getPublicationTasksByProgress(publicationId, ProgressStatus.NOT_STARTED);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping(value = "/{publicationId}/inProgressTasks")
    ResponseEntity<List<TaskDTO>> getInProgressPublicationTasks(@PathVariable Long publicationId) {
        List<TaskDTO> tasks = taskService.getPublicationTasksByProgress(publicationId, ProgressStatus.IN_PROGRESS);
        return ResponseEntity.ok(tasks);
    }


    @GetMapping(value = "/{publicationId}/completedTasks")
    ResponseEntity<List<TaskDTO>> getCompletedPublicationTasks(@PathVariable Long publicationId) {
        List<TaskDTO> tasks = taskService.getPublicationTasksByProgress(publicationId, ProgressStatus.COMPLETED);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(value = "/comment")
    ResponseEntity<TaskDTO> addComment(@Valid @RequestBody CommentDTO commentDTO) {
        TaskDTO task = taskService.addComment(commentDTO);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/deleteComment")
    ResponseEntity<?> deleteTaskComment(@RequestParam Long id) {
        taskService.deleteTaskComment(id);
        return ResponseEntity.noContent().build();
    }
}
