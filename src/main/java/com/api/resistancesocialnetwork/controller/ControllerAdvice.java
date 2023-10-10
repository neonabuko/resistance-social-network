package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String mostSpecificCause = e.getMostSpecificCause().getMessage();
        String localizedErrorInJSON = "";
        if (mostSpecificCause.contains("line")) {
            localizedErrorInJSON = mostSpecificCause
                    .substring(mostSpecificCause.indexOf("line")).replace("]", "");
        }

        if (mostSpecificCause.contains("Numeric value") && mostSpecificCause.contains("out of range"))
            return ResponseEntity.status(400).body("Value out of range\n" + localizedErrorInJSON);

        else return ResponseEntity.status(400).body(mostSpecificCause);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<String> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(ResistanceSocialNetworkException.class)
    public ResponseEntity<String> handleResistanceSocialNetworkException(ResistanceSocialNetworkException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(409).body(e.getReason());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(HttpStatus.valueOf(405));
    }
}