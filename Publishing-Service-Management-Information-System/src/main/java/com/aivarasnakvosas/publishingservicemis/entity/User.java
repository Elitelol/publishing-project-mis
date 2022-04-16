package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "User_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class User extends AbstractBasicEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Set<Publication> publications = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    private List<Publication> managedWorks = new ArrayList<>();

    @ManyToMany(mappedBy = "responsiblePeople")
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "commentator")
    private List<TaskComment> taskComments = new ArrayList<>();

    @OneToMany(mappedBy = "commentator")
    private List<BudgetComment> budgetComments = new ArrayList<>();

    @OneToMany(mappedBy = "commentator")
    private List<ContractComment> contractComments = new ArrayList<>();

}
