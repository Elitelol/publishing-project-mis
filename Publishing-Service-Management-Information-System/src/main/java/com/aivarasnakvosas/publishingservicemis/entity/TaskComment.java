package com.aivarasnakvosas.publishingservicemis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
}
