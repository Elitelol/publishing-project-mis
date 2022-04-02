package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.LoginFormDTO;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.security.JWT.JWTResponse;
import com.aivarasnakvosas.publishingservicemis.security.JWT.JWTUtils;
import com.aivarasnakvosas.publishingservicemis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "api/access")
public class AccessController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<JWTResponse> loginUser(@RequestBody LoginFormDTO loginFormDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginFormDTO.getUsername(), loginFormDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new JWTResponse(jwtToken));
    }

    @PostMapping(value = "/signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerNewUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createNewUser(userDTO));
    }
}
