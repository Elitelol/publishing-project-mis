package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "Attachment_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class Attachment extends AbstractBasicEntity {

    private String fileName;

    private String contentType;
}
