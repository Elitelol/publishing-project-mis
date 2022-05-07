package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.UserView;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import com.aivarasnakvosas.publishingservicemis.mappers.UserDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapper userDTOMapper;

    public UserView saveUser(UserDTO userDTO) {
        User user = userDTO.getId() != null ? findUser(userDTO.getId()) : new User();
        userDTOMapper.mapToUser(user, userDTO);
        userRepository.save(user);
        return userDTOMapper.mapToView(user);
    }

    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }
        return user.get();
    }

    public UserView getUser(Long id) {
        User user = findUser(id);
        return userDTOMapper.mapToView(user);
    }

    public List<User> findUsers(List<Long> ids) {
        return userRepository.findUsersById(ids);
    }

    public List<UserView> getUsers(List<Long> ids) {
        List<User> users = findUsers(ids);
        return users.stream()
                .map(user -> userDTOMapper.mapToView(user))
                .collect(Collectors.toList());
    }

    public List<UserView> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> userDTOMapper.mapToView(user))
                .collect(Collectors.toList());
    }

    public User findUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }
        return user.get();
    }

    public UserView getUserByUsername(String username) {
        User user = findUserByUsername(username);
        return userDTOMapper.mapToView(user);
    }
}
