package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.dtos.AttachmentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.AttachmentType;
import com.aivarasnakvosas.publishingservicemis.mappers.AttachmentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.AttachmentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.PublicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void saveAttachment(MultipartFile multipartFile, String attachmentDTO) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AttachmentDTO attachDTO = objectMapper.readValue(attachmentDTO, AttachmentDTO.class);
        Publication publication = publicationRepository.findById(attachDTO.getPublicationId()).get();
        Attachment attachment = attachmentDTOMapper.mapToAttachment(multipartFile, attachDTO, publication);
        attachmentRepository.save(attachment);
    }

    public List<AttachmentDTO> getPublicationAttachments(Long publicationId, AttachmentType attachmentType) {
        List<Attachment> attachments = attachmentRepository.findAttachmentByPublicationIdAndAttachmentType(publicationId, attachmentType);
        return attachments.stream()
                .map(attachment -> attachmentDTOMapper.mapToDTO(attachment))
                .collect(Collectors.toList());
    }

    public Attachment getAttachment(String id) {
        return attachmentRepository.findAttachmentByAttachmentId(id);
    }

    public void deleteAttachment(String id) {
        attachmentRepository.deleteById(id);
    }
}
