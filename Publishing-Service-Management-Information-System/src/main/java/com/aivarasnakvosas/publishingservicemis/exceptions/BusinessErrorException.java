package com.aivarasnakvosas.publishingservicemis.exceptions;

/**
 * @author Aivaras Nakvosas
 */
public class BusinessErrorException extends RuntimeException {

    public BusinessErrorException(String message) {
        super(message);
    }
}
