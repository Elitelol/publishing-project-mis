package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.UserDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.UserView;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.mappers.UserDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.UserRepository;
import com.aivarasnakvosas.publishingservicemis.utils.EntityGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Aivaras Nakvosas
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDTOMapper userDTOMapper;

    @Test
    void testSave() {
        UserDTO userDTO = EntityGenerator.createUserDTO(1L, "new");
        UserView userView = EntityGenerator.createUserView(1L, "new");
        User user = EntityGenerator.createUser(1L, "test");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userDTOMapper.mapToView(user)).thenReturn(userView);
        UserView result = userService.saveUser(userDTO);
        assertEquals("new", result.getUsername());
    }
}
