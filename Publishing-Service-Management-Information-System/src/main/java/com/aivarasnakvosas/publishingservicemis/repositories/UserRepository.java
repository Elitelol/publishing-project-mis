package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface UserRepository extends JpaRepository<AbstractUser, Long> {

}