package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    ResponseEntity<Task> saveTask(@RequestBody TaskDTO taskDTO){
        Task task = taskService.saveTask(taskDTO);
        return ResponseEntity.ok(task);
    }
}
