package com.aivarasnakvosas.publishingservicemis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Aivaras Nakvosas
 */
@Component
@ConfigurationProperties("application.security")
@Data
public class SecurityProperties {

    private String jwtTokenSecret;
    private Integer jwtExpirationTime;
}
