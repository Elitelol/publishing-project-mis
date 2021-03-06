package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import com.aivarasnakvosas.publishingservicemis.utilities.EntityCreationResponseMessage;
import com.aivarasnakvosas.publishingservicemis.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EntityCreationResponseMessage> saveAttachment(
            @RequestParam String attachmentDTO,
            @RequestParam MultipartFile file) {
        try {
            attachmentService.saveAttachment(file, attachmentDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new EntityCreationResponseMessage("File uploaded successfully."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new EntityCreationResponseMessage("Could not upload the file."));
        }
    }

    @GetMapping(value = "/{publicationId}/manuscript")
    public ResponseEntity<List<AttachmentDTO>> getPublicationManuscriptAttachments(@PathVariable Long publicationId) {
        return ResponseEntity.ok(attachmentService.getPublicationAttachments(publicationId, AttachmentType.MANUSCRIPT));
    }

    @GetMapping(value = "/{publicationId}/contract")
    public ResponseEntity<List<AttachmentDTO>> getPublicationContractAttachments(@PathVariable Long publicationId) {
        return ResponseEntity.ok(attachmentService.getPublicationAttachments(publicationId, AttachmentType.CONTRACT));
    }

    @GetMapping(value = "/{publicationId}/task")
    public ResponseEntity<List<AttachmentDTO>> getPublicationTaskAttachments(@PathVariable Long publicationId) {
        return ResponseEntity.ok(attachmentService.getPublicationAttachments(publicationId, AttachmentType.TASK_FILE));
    }

    @GetMapping(value = "/{publicationId}/cover")
    public ResponseEntity<List<AttachmentDTO>> getPublicationCoverAttachments(@PathVariable Long publicationId) {
        return ResponseEntity.ok(attachmentService.getPublicationAttachments(publicationId, AttachmentType.COVER_PHOTO));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable String id) {
        Attachment attachment = attachmentService.getAttachment(id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(attachment.getContentType()));
        header.setContentLength(attachment.getFile().length);
        header.set("Content-Disposition", "attachment; filename=" + attachment.getFileName());
        return new ResponseEntity<>(attachment.getFile(), header, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAttachment(@PathVariable String id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }
}
