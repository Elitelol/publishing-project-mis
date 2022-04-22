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
public enum Language {

    ENGLISH("English"),
    LITHUANIAN("Lithuanian"),
    GERMAN("German"),
    RUSSIAN("Russian"),
    POLISH("Polish"),
    DUTCH("Dutch"),
    SWEDISH("Swedish"),
    NORWEGIAN("Norwegian"),
    DANISH("Danish"),
    FINNISH("Finnish"),
    SPANISH("Spanish"),
    PORTUGUESE("Portuguese"),
    FRENCH("French");

    private final String language;

    public static Language getLanguage(String languageName) {
        return Arrays.stream(values())
                .filter(language -> language.getLanguage().equals(languageName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(PublicationType.class));
    }
}
