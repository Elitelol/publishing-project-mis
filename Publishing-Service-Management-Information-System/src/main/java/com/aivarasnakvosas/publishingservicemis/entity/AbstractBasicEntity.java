package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractBasicEntity {

    public static final String ID_FIELD = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    protected Long id;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIME)
    protected Date dateCreated;

    @LastModifiedDate
    @Temporal(TemporalType.TIME)
    protected Date dateModified;

}
