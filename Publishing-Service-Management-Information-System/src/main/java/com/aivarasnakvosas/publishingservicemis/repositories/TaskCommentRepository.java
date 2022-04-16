package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aivaras Nakvosas
 */
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
}
