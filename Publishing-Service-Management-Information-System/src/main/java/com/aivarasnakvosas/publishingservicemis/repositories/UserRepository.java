package com.aivarasnakvosas.publishingservicemis.repositories;

import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT u FROM User u WHERE u.id IN :Ids")
    List<User> findUsersById(@Param("Ids") List<Long> Ids);

    List<User> findUsersByRole(Role role);
}
