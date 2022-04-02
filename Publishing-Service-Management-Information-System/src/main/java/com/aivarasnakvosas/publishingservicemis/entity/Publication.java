package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.utilities.PublicationType;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aivaras Nakvosas
 */
@Entity
@AttributeOverride(name = AbstractBasicEntity.ID_FIELD, column = @Column(name = "Publication_Id", unique = true, nullable = false))
@NoArgsConstructor
@Getter
@Setter
public class Publication extends AbstractBasicEntity {

    private String name;

    private String isbn;

    private Long pageNumber;

    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "publication_authors",
            joinColumns = @JoinColumn(name ="User_Id"),
            inverseJoinColumns = @JoinColumn(name = "Publication_Id"))
    private Set<User> authors = new HashSet<>();

    @JsonIgnore
    @JoinColumn(name = "Manager_Id", referencedColumnName = "User_Id")
    @ManyToOne
    private User manager;

    public void addAuthor(User author) {
        authors.add(author);
    }
}
