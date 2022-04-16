package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String attachmentId;

    private String fileName;

    @Lob
    private byte [] file;

    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    private String contentType;

    @JoinColumn(name = "Publication_Id", referencedColumnName = "Publication_Id")
    @ManyToOne
    private Publication publication;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    protected Date dateCreated = new Date();

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    protected Date dateModified = new Date();
}
