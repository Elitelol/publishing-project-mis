package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.Comment;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        if (comment.getRootComment() != null) {
            commentDTO.setRootCommentId(comment.getRootComment().getId());
        }
        commentDTO.setText(comment.getText());
        commentDTO.setUserId(comment.getCommentator().getId());
        if (comment.getReply() != null && !comment.getReply().isEmpty()) { //wtf is this
            List<CommentDTO> replies = comment.getReply()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            commentDTO.setReplies(replies);
        }
        return commentDTO;
    }
}
