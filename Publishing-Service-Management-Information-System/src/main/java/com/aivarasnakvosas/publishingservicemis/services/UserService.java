package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.entity.AbstractUser;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aivaras Nakvosas
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public AbstractUser createNewUser(UserDTO userDTO) {
        return null;
    }

}
