package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.mappers.AttachmentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.AttachmentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aivaras Nakvosas
 */
@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private AttachmentDTOMapper attachmentDTOMapper;

    public Attachment saveAttachment(AttachmentDTO attachmentDTO) {
        Publication publication = publicationRepository.findById(attachmentDTO.getPublicationId()).get();
        Attachment attachment = attachmentDTOMapper.mapToAttachment(attachmentDTO, publication);
        return attachmentRepository.save(attachment);
    }

    public Attachment saveAttachment(AttachmentDTO attachmentDTO, Publication publication) {
        Attachment attachment = attachmentDTOMapper.mapToAttachment(attachmentDTO, publication);
        return attachmentRepository.save(attachment);
    }
}
