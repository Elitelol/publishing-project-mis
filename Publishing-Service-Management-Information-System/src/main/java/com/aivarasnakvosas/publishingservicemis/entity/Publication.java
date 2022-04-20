package com.aivarasnakvosas.publishingservicemis.entity;

import com.aivarasnakvosas.publishingservicemis.entity.enums.Genre;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Language;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.enums.PublicationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;

    private String rejectionReason;

    private String isbn;

    private Long pageNumber;

    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private Long price;

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    private boolean contractSigned;

    @ManyToMany
    @JoinTable(name = "publication_authors",
            joinColumns = @JoinColumn(name ="Publication_Id"),
            inverseJoinColumns = @JoinColumn(name = "User_Id"))
    private Set<User> authors = new HashSet<>();

    @JoinColumn(name = "Manager_Id", referencedColumnName = "User_Id")
    @ManyToOne
    private User manager;

    @OneToMany(mappedBy = "publication", orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToOne(mappedBy = "publication", orphanRemoval = true)
    private Contract contract;

    @OneToOne(mappedBy = "publication", orphanRemoval = true)
    private PublishingBudget publishingBudget;

    @OneToMany(mappedBy = "publication", orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public void addAuthor(User author) {
        authors.add(author);
    }
}
