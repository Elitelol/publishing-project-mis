package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TaskComment extends Comment {

    @JoinColumn(name = "Task_Id", referencedColumnName = "Task_Id")
    @ManyToOne
    private Task task;

    @JoinColumn(name = "ParentComment_Id", referencedColumnName = "Comment_Id")
    @ManyToOne
    private TaskComment rootComment;

    @OneToMany(mappedBy = "rootComment", orphanRemoval = true)
    private List<TaskComment> reply = new ArrayList<>();
}
