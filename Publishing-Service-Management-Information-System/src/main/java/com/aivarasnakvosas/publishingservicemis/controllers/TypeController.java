package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Genre;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Language;
import com.aivarasnakvosas.publishingservicemis.entity.enums.ProgressStatus;
import com.aivarasnakvosas.publishingservicemis.entity.enums.PublicationType;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Role;
import com.aivarasnakvosas.publishingservicemis.entity.enums.TaskType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value ="/type")
public class TypeController {

    @GetMapping(value = "/attachments")
    public ResponseEntity<List<AttachmentType>> getAttachmentTypes() {
        List<AttachmentType> attachmentTypes = Arrays.asList(AttachmentType.values());
        return ResponseEntity.ok(attachmentTypes);
    }

    @GetMapping(value = "/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        List<Genre> genres = Arrays.asList(Genre.values());
        return ResponseEntity.ok(genres);
    }

    @GetMapping(value = "/languages")
    public ResponseEntity<List<Language>> getLanguages() {
        List<Language> languages = Arrays.asList(Language.values());
        return ResponseEntity.ok(languages);
    }

    @GetMapping(value = "/publicationProgress")
    public ResponseEntity<List<ProgressStatus>> getPublicationProgress() {
        List<ProgressStatus> progressStatuses = ProgressStatus.getPublicationProgressStatus();
        return ResponseEntity.ok(progressStatuses);
    }

    @GetMapping(value = "/taskProgress")
    public ResponseEntity<List<ProgressStatus>> getTaskProgress() {
        List<ProgressStatus> progressStatuses = ProgressStatus.getTaskProgressStatus();
        return ResponseEntity.ok(progressStatuses);
    }

    @GetMapping(value = "/publications")
    public ResponseEntity<List<PublicationType>> getPublications() {
        List<PublicationType> publicationTypes = Arrays.asList(PublicationType.values());
        return ResponseEntity.ok(publicationTypes);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = Arrays.asList(Role.values());
        return ResponseEntity.ok(roles);
    }

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<TaskType>> getTasks() {
        List<TaskType> taskTypes = Arrays.asList(TaskType.values());
        return ResponseEntity.ok(taskTypes);
    }
}
