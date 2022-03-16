package com.aivarasnakvosas.publishingservicemis.exceptions;

import javax.naming.AuthenticationException;

/**
 * @author Aivaras Nakvosas
 */
public class InvalidDataException extends AuthenticationException {

    public InvalidDataException(String message) {
        super(message);
    }
}
