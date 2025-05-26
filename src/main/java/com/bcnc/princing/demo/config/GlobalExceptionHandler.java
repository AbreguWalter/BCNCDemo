package com.bcnc.princing.demo.config;

import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", ZonedDateTime.now(),
                "error", "NOT_FOUND",
                "message", ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleAppError(ApplicationException ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", ZonedDateTime.now(),
                "error", "APPLICATION_ERROR",
                "message", ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpected(Exception ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", ZonedDateTime.now(),
                "error", "INTERNAL_ERROR",
                "message", ex.getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
