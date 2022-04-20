package com.aivarasnakvosas.publishingservicemis.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {

    private Long commentId;
    private Long userId;
    private Long entityId;
    private String text;
    private Long rootCommentId;
    private List<CommentDTO> replies;
}
