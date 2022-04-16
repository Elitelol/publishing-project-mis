package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import com.aivarasnakvosas.publishingservicemis.utilities.AttachmentResponseMessage;
import com.aivarasnakvosas.publishingservicemis.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AttachmentResponseMessage> saveAttachment(
            @RequestPart("attachmentDTO") String attachmentDTO,
            @RequestPart("file") MultipartFile file) {
        try {
            attachmentService.saveAttachment(file, attachmentDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new AttachmentResponseMessage("File uploaded successfully."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new AttachmentResponseMessage("Could not upload the file."));
        }
    }

    @GetMapping
    public ResponseEntity<List<AttachmentDTO>> getPublicationAttachments(@RequestParam Long publicationId) {
        return ResponseEntity.ok(attachmentService.getPublicationAttachments(publicationId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> getAttachment(@PathVariable String id) {
        Attachment attachment = attachmentService.getAttachment(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(new ByteArrayResource(attachment.getFile()));
    }

}
