package com.example.order_service.rest.advice;

import com.example.order_service.exception.OrderNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@NoArgsConstructor
public class ServiceExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFoundExceptionHandler(
            OrderNotFoundException productNotFoundException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                productNotFoundException.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
