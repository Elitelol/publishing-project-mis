package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class AttachmentDTOMapper {

    public Attachment mapToAttachment(MultipartFile file, AttachmentDTO attachmentDTO, Publication publication) throws IOException {
        Attachment attachment = new Attachment();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        attachment.setFileName(fileName);
        attachment.setFile(file.getBytes());
        attachment.setAttachmentType(AttachmentType.getType(attachmentDTO.getAttachmentType()));
        attachment.setContentType(file.getContentType());
        attachment.setPublication(publication);
        return attachment;
    }

    public AttachmentDTO mapToDTO(Attachment attachment) {
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/attachment/")
                .path(attachment.getAttachmentId())
                .toUriString();
        attachmentDTO.setPublicationId(attachment.getPublication().getId());
        attachmentDTO.setFileName(attachment.getFileName());
        attachmentDTO.setAttachmentType(attachment.getAttachmentType().getType());
        attachmentDTO.setContentType(attachment.getContentType());
        attachmentDTO.setUrl(fileDownloadUri);
        return attachmentDTO;
    }
}
