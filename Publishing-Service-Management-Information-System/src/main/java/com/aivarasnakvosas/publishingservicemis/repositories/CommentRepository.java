package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
