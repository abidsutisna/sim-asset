package com.asset.simasset.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.asset.simasset.dto.response.ResponseDTO;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseDTO.renderJson(null, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        return ResponseDTO.renderJson(null, e.getReason(), HttpStatus.BAD_REQUEST);
    }

      @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgument(MethodArgumentNotValidException e) {
        String message = "Validation Error";
        if (e.getBindingResult().hasErrors()) {
            message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseDTO.renderJson(null, message, status);
    }

}
