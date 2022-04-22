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
public enum Role {

    AUTHOR("Author"),
    PUBLICATION_MANAGER("Publication Manager"),
    EDITOR("Editor"),
    TRANSLATOR("Translator"),
    DESIGNER("Designer");

    private final String role;

    public static Role getRole(String roleName) {
        return Arrays.stream(values())
                .filter(role -> role.getRole().equals(roleName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(Role.class));
    }
}
