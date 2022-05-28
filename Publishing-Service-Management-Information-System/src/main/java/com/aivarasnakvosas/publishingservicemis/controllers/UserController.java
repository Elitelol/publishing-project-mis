package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.UserView;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Role;
import com.aivarasnakvosas.publishingservicemis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    ResponseEntity<UserView> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<UserView>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<UserView> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping(value = "/byUsername/{username}")
    ResponseEntity<UserView> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping(value = "/authors")
    ResponseEntity<List<UserView>> getAuthors() {
        return ResponseEntity.ok(userService.getUsersByRole(Role.AUTHOR));
    }

    @GetMapping(value = "/managers")
    ResponseEntity<List<UserView>> getManagers() {
        return ResponseEntity.ok(userService.getUsersByRole(Role.PUBLICATION_MANAGER));
    }

    @GetMapping(value = "/workers")
    ResponseEntity<List<UserView>> getWorkers() {
        return ResponseEntity.ok(userService.getUsersByRole(Role.WORKER));
    }
}
