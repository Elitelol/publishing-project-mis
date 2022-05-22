package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.TaskDTO;
import com.aivarasnakvosas.publishingservicemis.exceptions.BusinessErrorException;
import com.aivarasnakvosas.publishingservicemis.mappers.CommentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.TaskDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskRepository;
import com.aivarasnakvosas.publishingservicemis.utils.EntityGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Aivaras Nakvosas
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskCommentRepository taskCommentRepository;
    @Mock
    private TaskDTOMapper taskDTOMapper;
    @Mock
    private CommentDTOMapper commentDTOMapper;
    @Mock
    private PublicationService publicationService;
    @Mock
    private UserService userService;

    @Test
    void testSaveTaskWhenDateIsInvalid() throws ParseException {
        TaskDTO taskDTO = EntityGenerator.createTaskDTO(new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-12"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-12"));
        assertThrows(BusinessErrorException.class, () -> taskService.saveTask(taskDTO));
    }
}
