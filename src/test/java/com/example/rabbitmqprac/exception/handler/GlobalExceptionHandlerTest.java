package com.example.rabbitmqprac.exception.handler;

import com.example.rabbitmqprac.exception.exceptionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(exceptionController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenResourceNotFoundExceptionThrown_thenRespondWith404() throws Exception {
        mockMvc.perform(get("/test/resource-not-found")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }

    @Test
    public void whenIlLegalArgumentExceptionThrown_thenRespondWith400() throws Exception {
        mockMvc.perform(get("/test/illegal-argument")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Illegal argument"));
    }

    @Test
    public void whenDataAccessExceptionThrown_thenRespondWith404() throws Exception {
        mockMvc.perform(get("/test/data-access")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Data access error"));
    }

    @Test
    public void whenAccessDeniedExceptionThrown_thenResponseWith403() throws Exception {
        mockMvc.perform(get("/test/access-denied")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message").value("Access denied"));
    }

    @Test
    public void WhenNoHandlerFoundExceptionThrown_thenResponseWith404() throws Exception {
        mockMvc.perform(get("/test/no-handler-found")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No handler found"));
    }

    @Test
    public void WhenExceptionThrown_thenResponseWith500() throws Exception {
        mockMvc.perform(get("/test/exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Exception"));
    }
}