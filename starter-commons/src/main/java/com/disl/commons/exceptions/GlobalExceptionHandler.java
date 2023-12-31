package com.disl.commons.exceptions;

import com.disl.commons.payloads.Response;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Response errorResponse = new Response(HttpStatus.NOT_FOUND, false, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        Response errorResponse = new Response(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
