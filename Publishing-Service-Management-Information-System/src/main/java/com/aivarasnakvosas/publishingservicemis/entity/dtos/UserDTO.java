package com.aivarasnakvosas.publishingservicemis.entity.dtos;

import com.aivarasnakvosas.publishingservicemis.entity.utilities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
