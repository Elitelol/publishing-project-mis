package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Aivaras Nakvosas
 */
@Getter
@Setter
public class LoginFormDTO {

    @NotBlank(message = "Username can't be empty")
    private String username;
    @NotBlank(message = "Password can't be empty")
    private String password;
}
