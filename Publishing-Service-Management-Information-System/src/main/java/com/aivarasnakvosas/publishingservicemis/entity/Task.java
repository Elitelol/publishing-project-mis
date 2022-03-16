package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.utilities.TaskType;

import java.util.Date;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
//@Entity(name = "Task")
public class Task extends AbstractBasicEntity {

    private TaskType taskType;

    private String taskName;

    private String description;

    private Date startDate;

    private Date dueDate;

    private List<Employee> responsiblePeople;

    // TODO Think this through
    //private List<String> relatedFiles;
}
