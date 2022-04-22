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
public enum TaskType {

    EDITING("Editing"),
    DESIGN("Design");

    private final String type;

    public static TaskType getTask(String typeName) {
        return Arrays.stream(values())
                .filter(taskType -> taskType.getType().equals(typeName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(PublicationType.class));
    }
}
