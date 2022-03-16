package com.aivarasnakvosas.publishingservicemis.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Aivaras Nakvosas
 */
@Getter
@Setter
public class UserContext {

    private Long userId;
    private String firstName;
    private String lastName;

}
