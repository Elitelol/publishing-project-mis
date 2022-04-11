package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    private String contentType;

    @JsonIgnore
    @JoinColumn(name = "Publication_Id", referencedColumnName = "Publication_Id")
    @ManyToOne
    private Publication publication;
}
