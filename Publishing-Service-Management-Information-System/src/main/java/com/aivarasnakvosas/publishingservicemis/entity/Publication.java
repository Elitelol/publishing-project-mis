package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.utilities.PublicationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


/**
 * @author Aivaras Nakvosas
 */
//@Entity
public class Publication extends AbstractBasicEntity {

    @Column(name = "PublicationType")
    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;

    @ManyToOne
    private Author author;

    @ManyToMany
    private Employee employee;

    private

}
