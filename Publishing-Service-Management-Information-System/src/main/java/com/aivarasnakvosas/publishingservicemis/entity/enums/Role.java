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
public enum Role {

    AUTHOR("Author"),
    PUBLICATION_MANAGER("Publication Manager"),
    WORKER("Worker");

    private final String role;

    public static Role getRole(String roleName) {
        return Arrays.stream(values())
                .filter(role -> role.getRole().equals(roleName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(Role.class));
    }
}
