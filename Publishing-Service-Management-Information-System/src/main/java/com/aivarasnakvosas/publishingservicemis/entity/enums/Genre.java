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
public enum Genre {

    FANTASY("Fantasy"),
    ACTION_AND_ADVENTURE("Action and Adventure"),
    HORROR("Horror"),
    SCIENCE_FICTION("Science Fiction"),
    ROMANCE("Romance"),
    CHILDREN("Children"),
    GRAPHIC_NOVEL("Graphic Novel"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    THRILLER("Thriller"),
    DETECTIVE("Detective"),
    HISTORICAL_FICTION("Historical Fiction");

    private final String genre;

    public static Genre getGenre(String genreName) {
        return Arrays.stream(values())
                .filter(genre -> genre.getGenre().equals(genreName)).
                        findFirst().orElseThrow(() -> new EntityNotFoundException(Genre.class));
    }
}
