package com.aivarasnakvosas.publishingservicemis.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Aivaras Nakvosas
 */
@MappedSuperclass
public abstract class AbstractBasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    protected Long id;

    @CreatedDate
    @Column(name = "DateCreated", updatable = false)
    @Temporal(TemporalType.TIME)
    protected Date dateCreated;

    @LastModifiedDate
    @Column(name = "DateModified")
    @Temporal(TemporalType.TIME)
    protected Date dateModified;

}
