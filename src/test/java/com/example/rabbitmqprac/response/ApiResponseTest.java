package com.example.rabbitmqprac.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiResponseTest {

    @Test
    public void testOkWithOnlyData() {
        ApiResponse<String> response = ApiResponse.ok("Only Data");
        assertEquals(200, response.getStatus());
        assertEquals("OK", response.getMessage());
        assertEquals("Only Data", response.getData());
    }

    @Test
    public void testOkWithAllParams() {
        ApiResponse<String> response = ApiResponse.ok(201, "Created", "All Params");
        assertEquals(201, response.getStatus());
        assertEquals("Created", response.getMessage());
        assertEquals("All Params", response.getData());
    }

    @Test
    public void testFailWithOnlyMessage() {
        ApiResponse<String> response = ApiResponse.fail("Only Message");
        assertEquals(500, response.getStatus());
        assertEquals("Only Message", response.getMessage());
        assertEquals(null, response.getData());
    }

    @Test
    public void testFailWithAllParams() {
        ApiResponse<String> response = ApiResponse.fail(400, "Bad Request");
        assertEquals(400, response.getStatus());
        assertEquals("Bad Request", response.getMessage());
        assertEquals(null, response.getData());
    }
}
