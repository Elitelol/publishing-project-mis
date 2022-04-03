package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
