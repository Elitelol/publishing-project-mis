package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/test")
    public String testingMethod() {
        return "hello";
    }
}
