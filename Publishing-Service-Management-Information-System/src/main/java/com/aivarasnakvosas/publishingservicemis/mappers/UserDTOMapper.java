package com.aivarasnakvosas.publishingservicemis.mappers;


import com.aivarasnakvosas.publishingservicemis.dtos.UserView;
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

    public UserView mapToView(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setUsername(user.getUsername());
        userView.setFirstName(user.getFirstName());
        userView.setLastName(user.getLastName());
        userView.setEmail(user.getEmail());
        userView.setRole(user.getRole().getRole());
        return userView;
    }
}
