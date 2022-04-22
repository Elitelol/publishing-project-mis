package com.aivarasnakvosas.publishingservicemis.mappers;


import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.entity.enums.Role;
import com.aivarasnakvosas.publishingservicemis.exceptions.BusinessErrorException;
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

    public void mapToUser(User user, UserDTO userDTO) {
        user.setUsername(userDTO.getUsername());
        String password = userDTO.getPassword();
        if (user.getId() == null && (password == null || password.isBlank())) {
            throw new BusinessErrorException("Password can't be empty.");
        }
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRole(Role.getRole(userDTO.getRole()));
    }

    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().getRole());
        return userDTO;
    }
}
