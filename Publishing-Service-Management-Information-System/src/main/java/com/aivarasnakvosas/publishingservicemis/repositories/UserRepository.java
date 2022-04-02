package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findtUserByUsername(String username);
}
