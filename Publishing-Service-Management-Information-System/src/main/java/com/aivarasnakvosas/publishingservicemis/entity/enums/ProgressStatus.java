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
public enum ProgressStatus {

    NOT_SUBMITTED("Not Submitted"),
    NOT_STARTED("Not Started"),
    IN_REVIEW("In Review"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    PUBLISHED("Published");

    private final String status;

    public static ProgressStatus getStatus(String typeName) {
        return Arrays.stream(values())
                .filter(progressStatus -> progressStatus.getStatus().equals(typeName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(ProgressStatus.class));
    }
}
