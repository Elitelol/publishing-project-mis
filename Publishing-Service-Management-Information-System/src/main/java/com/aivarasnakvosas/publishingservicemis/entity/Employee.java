package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.utilities.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

/**
 * @author Aivaras Nakvosas
 */
@Entity(name = "Employee")
public class Employee extends AbstractUser {

    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    Role role;

    @ManyToMany
    private Publication publication;
}
