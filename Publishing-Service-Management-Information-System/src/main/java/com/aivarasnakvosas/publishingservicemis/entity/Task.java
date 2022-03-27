package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.utilities.TaskType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "Task_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class Task extends AbstractBasicEntity {

    @JoinColumn(name = "Publication_Id", referencedColumnName = "Publication_Id")
    @ManyToOne
    private Publication publication;

    private TaskType taskType;

    private String taskName;

    private String description;

    private Date startDate;

    private Date dueDate;

    @ManyToMany
    @JoinTable(name = "task_users",
            joinColumns = @JoinColumn(name ="User_Id"),
            inverseJoinColumns = @JoinColumn(name = "Task_Id"))
    private List<User> responsiblePeople;

    // TODO Think this through
    //private List<String> relatedFiles;
}
