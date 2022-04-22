package com.aivarasnakvosas.publishingservicemis.entity.enums;

import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Aivaras Nakvosas
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
