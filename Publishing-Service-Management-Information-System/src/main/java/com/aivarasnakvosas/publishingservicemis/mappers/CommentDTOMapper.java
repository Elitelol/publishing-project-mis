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

    private void mapToComment(CommentDTO commentDTO, User user, Comment comment) {
        comment.setCommentator(user);
        comment.setText(commentDTO.getText());
    }

    public TaskComment mapToTaskComment(CommentDTO commentDTO, Task task, User user, TaskComment rootComment) {
        TaskComment taskComment = new TaskComment();
        mapToComment(commentDTO, user, taskComment);
        taskComment.setRootComment(rootComment);
        taskComment.setTask(task);
        return taskComment;
    }

    public BudgetComment mapToBudgetComment(CommentDTO commentDTO, PublishingBudget task, User user, BudgetComment rootComment) {
        BudgetComment budgetComment = new BudgetComment();
        mapToComment(commentDTO, user, budgetComment);
        budgetComment.setRootComment(rootComment);
        budgetComment.setPublishingBudget(task);
        return budgetComment;
    }

    public ContractComment mapToContractComment(CommentDTO commentDTO, Contract contract, User user, ContractComment rootComment) {
        ContractComment contractComment = new ContractComment();
        mapToComment(commentDTO, user, contractComment);
        contractComment.setRootComment(rootComment);
        contractComment.setContract(contract);
        return contractComment;
    }

    public CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText(comment.getText());
        commentDTO.setUserId(comment.getCommentator().getId());
        if (comment instanceof TaskComment) {
            mapRootComments((TaskComment) comment, commentDTO);
        } else if (comment instanceof BudgetComment) {
            mapRootComments((BudgetComment) comment, commentDTO);
        } else {
            mapRootComments((ContractComment) comment, commentDTO);
        }
        return commentDTO;
    }

    //awful

    private void mapRootComments(TaskComment comment, CommentDTO commentDTO) {
        if (comment.getRootComment() != null) {
            commentDTO.setRootCommentId(comment.getRootComment().getId());
        }
        if (comment.getReply() != null && !comment.getReply().isEmpty()) { //wtf is this
            List<CommentDTO> replies = comment.getReply()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            commentDTO.setReplies(replies);
        }
    }

    private void mapRootComments(BudgetComment comment, CommentDTO commentDTO) {
        if (comment.getRootComment() != null) {
            commentDTO.setRootCommentId(comment.getRootComment().getId());
        }
        if (comment.getReply() != null && !comment.getReply().isEmpty()) { //wtf is this
            List<CommentDTO> replies = comment.getReply()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            commentDTO.setReplies(replies);
        }
    }

    private void mapRootComments(ContractComment comment, CommentDTO commentDTO) {
        if (comment.getRootComment() != null) {
            commentDTO.setRootCommentId(comment.getRootComment().getId());
        }
        if (comment.getReply() != null && !comment.getReply().isEmpty()) { //wtf is this
            List<CommentDTO> replies = comment.getReply()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            commentDTO.setReplies(replies);
        }
    }
}
