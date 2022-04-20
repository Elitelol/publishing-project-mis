package com.aivarasnakvosas.publishingservicemis.exceptions;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Aivaras Nakvosas
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class entity) {
        super(EntityNotFoundException.buildMessage(entity));
    }

    private static String buildMessage(Class entity) {
        return StringUtils.capitalize(entity.getSimpleName()) + " wasn't found." ;
    }
}
