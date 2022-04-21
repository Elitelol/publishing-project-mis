package com.aivarasnakvosas.publishingservicemis.exceptions.handler;

import com.aivarasnakvosas.publishingservicemis.exceptions.BusinessErrorException;
import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Aivaras Nakvosas
 */
@ControllerAdvice
public class RESTExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessErrorException.class)
    public ResponseEntity<Object> handleBusinessErrorException(BusinessErrorException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), httpStatus.value(), httpStatus, exception.getMessage());
        return buildResponse(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), httpStatus.value(), httpStatus, exception.getMessage());
        return buildResponse(errorMessage);
    }

    private ResponseEntity<Object> buildResponse(ErrorMessage errorMessage) {
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }
}
