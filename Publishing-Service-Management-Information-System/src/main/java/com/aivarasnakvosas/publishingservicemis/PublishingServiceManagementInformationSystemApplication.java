package com.aivarasnakvosas.publishingservicemis;

import com.aivarasnakvosas.publishingservicemis.config.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Aivaras Nakvosas
 */
@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class PublishingServiceManagementInformationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishingServiceManagementInformationSystemApplication.class, args);
    }

}
