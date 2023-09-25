package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.rules.TradeFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String mostSpecificCause = e.getMostSpecificCause().getMessage();
        String localizedError = mostSpecificCause.substring(mostSpecificCause.indexOf("line")).replace("]", "");

        if (mostSpecificCause.contains("Numeric value") && mostSpecificCause.contains("out of range"))
            return ResponseEntity.status(400).body("Number too large at " + localizedError);

        else return ResponseEntity.status(400).body(mostSpecificCause);
    }
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<String> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(TradeFailureException.class)
    public ResponseEntity<String> handleTradeFailureException(TradeFailureException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}