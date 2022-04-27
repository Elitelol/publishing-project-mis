package com.aivarasnakvosas.publishingservicemis.security.JWT;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Aivaras Nakvosas
 */
@AllArgsConstructor
@Getter
public class JWTResponse {

    private final String token;
    private final Long id;
    private final String role;
}
