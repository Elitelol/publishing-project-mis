package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.utilities.AttachmentType;
import org.springframework.stereotype.Component;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class AttachmentDTOMapper {

    public Attachment mapToAttachment(AttachmentDTO attachmentDTO, Publication publication){
        Attachment attachment = new Attachment();
        attachment.setFileName(attachmentDTO.getFileName());
        attachment.setAttachmentType(AttachmentType.valueOf(attachmentDTO.getAttachmentType()));
        attachment.setContentType(attachment.getContentType());
        attachment.setPublication(publication);
        return attachment;
    }
}
