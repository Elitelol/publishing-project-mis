package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.LoginFormDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.security.JWT.JWTResponse;
import com.aivarasnakvosas.publishingservicemis.security.JWT.JWTUtils;
import com.aivarasnakvosas.publishingservicemis.security.UserContext;
import com.aivarasnakvosas.publishingservicemis.services.UserService;
import com.aivarasnakvosas.publishingservicemis.utilities.AuthUser;
import com.aivarasnakvosas.publishingservicemis.utilities.AuthUserResponse;
import com.aivarasnakvosas.publishingservicemis.utilities.EntityCreationResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/access")
public class AccessController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<JWTResponse> loginUser(@Valid @RequestBody LoginFormDTO loginFormDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginFormDTO.getUsername(), loginFormDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return ResponseEntity.ok(new JWTResponse(jwtToken, userContext.getId(), userContext.getRole()));
    }

    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityCreationResponseMessage> registerNewUser(@Valid @RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        EntityCreationResponseMessage userCreationResponse = new EntityCreationResponseMessage("User created.");
        return ResponseEntity.ok(userCreationResponse);
    }

    @GetMapping(value = "/auth")
    public ResponseEntity<AuthUser> getAuthenticatedUser(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("auth").substring(6);
        String username = jwtUtils.getUsernameFromJwtToken(token);
        User user = userService.findUserByUsername(username);
        AuthUserResponse authUserResponse = new AuthUserResponse(user.getId(), user.getRole().getRole());
        return ResponseEntity.ok(new AuthUser(authUserResponse));
    }
}
