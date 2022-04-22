package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    @NotBlank(message = "Username can't be empty.")
    private String username;
    private String password;
    @NotBlank(message = "First name can't be empty.")
    private String firstName;
    @NotBlank(message = "Last name can't be empty.")
    private String lastName;
    @NotBlank(message = "Email can't be empty.")
    private String email;
    private String role;
}
