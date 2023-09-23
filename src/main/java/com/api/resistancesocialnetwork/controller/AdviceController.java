package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.rules.TradeFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler
    public ResponseEntity<String> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleTradeFailureException(TradeFailureException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}