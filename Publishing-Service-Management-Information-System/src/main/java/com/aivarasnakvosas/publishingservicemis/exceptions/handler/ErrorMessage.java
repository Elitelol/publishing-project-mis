package com.aivarasnakvosas.publishingservicemis.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@Data
@AllArgsConstructor
public class ErrorMessage {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private Integer httpCode;
    private HttpStatus httpStatus;
    private List<String> message = new ArrayList<>();
}
