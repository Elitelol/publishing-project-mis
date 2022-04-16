package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {

    Attachment findAttachmentByAttachmentId(String id);

    List<Attachment> findAttachmentByPublicationId(Long publicationId);
}
