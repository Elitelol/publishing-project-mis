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
public enum PublicationType {

    BOOK("Book"),
    MAGAZINE("Magazine"),
    CARD("Card");

    private final String type;

    public static PublicationType getType(String typeName) {
        return Arrays.stream(values())
                .filter(publicationType -> publicationType.getType().equals(typeName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(PublicationType.class));
    }
}
