package com.example.rabbitmqprac.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

@RestController
public class exceptionController {

    @GetMapping("/test/resource-not-found")
    public void throwResourceNotFoundException() {
        throw new ResourceNotFoundException("Resource not found");
    }

    @GetMapping("/test/illegal-argument")
    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException("Illegal argument");
    }

    @GetMapping("/test/data-access")
    public void throwDataAccessException() {
        throw new DataAccessResourceFailureException("Data access resource failure");
    }

    @GetMapping("/test/access-denied") //handleAccessDeniedException
    public void throwAccessDeniedException() throws AccessDeniedException {
        throw new AccessDeniedException("Access denied");
    }

    @GetMapping("/test/no-handler-found")
    public void throwNoHandlerFoundException() throws NoHandlerFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Test-Header", "Test Value");
        throw new NoHandlerFoundException("GET", "/test/no-handler-found", headers);//String httpMethod, String requestURLHttp, Headers headers
    }

    //handleException
    @GetMapping("/test/exception")
    public void throwException() throws Exception {
        throw new Exception("Exception");
    }
}
