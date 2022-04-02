package com.aivarasnakvosas.publishingservicemis.mappers;


import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.entity.utilities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class UserDTOMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));;
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
        return user;
    }
}
