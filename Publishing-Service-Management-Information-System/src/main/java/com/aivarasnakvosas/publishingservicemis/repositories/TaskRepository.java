package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aivaras Nakvosas
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
