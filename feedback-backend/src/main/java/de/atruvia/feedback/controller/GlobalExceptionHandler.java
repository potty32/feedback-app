package de.atruvia.feedback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationFehlerAntwort> handleValidationFehler(MethodArgumentNotValidException ex) {
        List<String> fehler = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationFehlerAntwort(fehler));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ValidationFehlerAntwort> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ValidationFehlerAntwort(List.of(ex.getMessage())));
    }

    record ValidationFehlerAntwort(List<String> fehler) {}
}
