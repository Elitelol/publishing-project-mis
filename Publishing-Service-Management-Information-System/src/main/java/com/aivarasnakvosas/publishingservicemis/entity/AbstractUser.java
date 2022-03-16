package com.aivarasnakvosas.publishingservicemis.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Aivaras Nakvosas
 */
@MappedSuperclass
public abstract class AbstractUser extends AbstractBasicEntity {

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;
}
