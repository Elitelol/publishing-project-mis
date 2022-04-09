package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.Comment;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class CommentDTOMapper {

    public Comment mapToComment(CommentDTO commentDTO, Task task, User user, Comment rootComment) {
        Comment comment = new Comment();
        comment.setRootComment(rootComment);
        comment.setTask(task);
        comment.setCommentator(user);
        comment.setText(commentDTO.getText());
        return comment;
    }
}
