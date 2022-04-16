package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.mappers.AttachmentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.AttachmentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aivaras Nakvosas
 */
@Service
@Transactional
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

    public void saveAttachment(AttachmentDTO attachmentDTO, Publication publication) {
        Attachment attachment = attachmentDTOMapper.mapToAttachment(attachmentDTO, publication);
        attachmentRepository.save(attachment);
    }
}
