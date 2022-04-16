package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    ResponseEntity<TaskDTO> saveTask(@RequestBody TaskDTO taskDTO){
        TaskDTO task = taskService.saveTask(taskDTO);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    ResponseEntity<TaskDTO> getTask(@RequestParam Long taskId) {
        TaskDTO task = taskService.getTask(taskId);
        return ResponseEntity.ok(task);
    }

    @PostMapping(value = "/comment")
    ResponseEntity<TaskDTO> addComment(@RequestBody CommentDTO commentDTO) {
        TaskDTO task = taskService.addComment(commentDTO);
        return ResponseEntity.ok(task);
    }
}
