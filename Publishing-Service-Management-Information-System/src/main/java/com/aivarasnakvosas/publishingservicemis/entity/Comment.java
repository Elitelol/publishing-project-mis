package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "Comment_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class Comment extends AbstractBasicEntity {

    @JoinColumn(name = "User_Id", referencedColumnName = "User_Id")
    @ManyToOne
    private User commentator;

    @JoinColumn(name = "Task_Id", referencedColumnName = "Task_Id")
    @ManyToOne
    private Task task;

    private String text;

    @JoinColumn(name = "ParentComment_Id", referencedColumnName = "Comment_Id")
    @ManyToOne
    private Comment rootComment;

    @OneToMany(mappedBy = "rootComment")
    private List<Comment> reply;

}

