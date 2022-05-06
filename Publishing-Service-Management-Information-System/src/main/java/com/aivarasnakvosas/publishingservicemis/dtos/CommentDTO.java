package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {

    private Long commentId;
    private UserView user;
    private Long entityId;
    private Date posted;
    @NotBlank(message = "Text can't be empty")
    private String text;
    private Long rootCommentId;
    private List<CommentDTO> replies = new ArrayList<>();
}
