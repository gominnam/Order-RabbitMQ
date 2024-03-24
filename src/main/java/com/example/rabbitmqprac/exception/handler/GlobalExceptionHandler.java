package com.example.rabbitmqprac.exception.handler;


import com.example.rabbitmqprac.exception.ResourceNotFoundException;
import com.example.rabbitmqprac.response.ApiResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), "Resource not found");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Illegal argument", null);
    }

    @ExceptionHandler(DataAccessException.class)
    public ApiResponse<?> handleDataAccessException(DataAccessException e) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data access error", null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<?> handleAccessDeniedException(AccessDeniedException e) {
        return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Access denied", null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No handler found", null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
    }
}
