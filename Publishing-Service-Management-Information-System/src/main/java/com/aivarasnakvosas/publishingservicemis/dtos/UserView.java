package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class UserView {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
