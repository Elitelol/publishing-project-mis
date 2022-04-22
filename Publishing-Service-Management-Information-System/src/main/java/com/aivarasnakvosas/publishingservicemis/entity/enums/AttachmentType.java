package com.aivarasnakvosas.publishingservicemis.entity.enums;

import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Aivaras Nakvosas
 */
@AllArgsConstructor
@Getter
public enum AttachmentType {

    MANUSCRIPT("Manuscript"),
    TASK_FILE("Task File"),
    CONTRACT("Contract"),
    COVER_PHOTO("Cover Photo");

    private final String type;

    public static AttachmentType getType(String typeName) {
        return Arrays.stream(values())
                .filter(attachmentType -> attachmentType.getType().equals(typeName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(AttachmentType.class));
    }
}
