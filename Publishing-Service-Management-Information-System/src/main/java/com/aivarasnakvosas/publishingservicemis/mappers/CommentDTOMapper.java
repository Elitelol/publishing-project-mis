package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.entity.BudgetComment;
import com.aivarasnakvosas.publishingservicemis.entity.Comment;
import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import com.aivarasnakvosas.publishingservicemis.entity.ContractComment;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.entity.Task;
import com.aivarasnakvosas.publishingservicemis.entity.TaskComment;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class CommentDTOMapper {

    private void mapToComment(CommentDTO commentDTO, User user, Comment comment, Comment rootComment) {
        comment.setRootComment(rootComment);
        comment.setCommentator(user);
        comment.setText(commentDTO.getText());
    }

    public TaskComment mapToTaskComment(CommentDTO commentDTO, Task task, User user, TaskComment rootComment) {
        TaskComment taskComment = new TaskComment();
        mapToComment(commentDTO, user, taskComment, rootComment);
        taskComment.setTask(task);
        return taskComment;
    }

    public BudgetComment mapToBudgetComment(CommentDTO commentDTO, PublishingBudget task, User user, BudgetComment rootComment) {
        BudgetComment budgetComment = new BudgetComment();
        mapToComment(commentDTO, user, budgetComment, rootComment);
        budgetComment.setPublishingBudget(task);
        return budgetComment;
    }

    public ContractComment mapToContractComment(CommentDTO commentDTO, Contract contract, User user, BudgetComment rootComment) {
        ContractComment contractComment = new ContractComment();
        mapToComment(commentDTO, user, contractComment, rootComment);
        contractComment.setContract(contract);
        return contractComment;
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
