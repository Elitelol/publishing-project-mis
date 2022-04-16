package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.enums.TaskType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private String taskName;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private boolean done;

    @ManyToMany
    @JoinTable(name = "task_users",
            joinColumns = @JoinColumn(name ="User_Id"),
            inverseJoinColumns = @JoinColumn(name = "Task_Id"))
    private List<User> responsiblePeople;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskComment> comments = new ArrayList<>();

    public void addComment(TaskComment comment) {
        comments.add(comment);
    }
}
