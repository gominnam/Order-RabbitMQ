package com.example.rabbitmqprac.controller;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.service.OrderProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class) //@WebMvcTest는 내장된 MockMvc 인스턴스를 제공
public class OrderControllerTest {

    @Autowired //Spring의 의존성 주입 기능을 사용하여 빈을 자동으로 연결
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderProcessingService orderProcessingService;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = Order.builder()
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-21T14:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .totalQuantity(3)
                .totalPrice(4000000)
                .build();
    }

    @Test
    public void createOrder_ShouldReturnOrder_WhenRequestIsValid() throws Exception {
        // given
        given(orderProcessingService.processOrder(any(Order.class))).willReturn(order);

        // When & Then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)) // body에 status가 200인지 확인
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.id").value(order.getId()));
    }

    @Test
    public void createOrder_ShouldReturnError_WhenServiceThrowsException() throws Exception {
        // given
        Order newOrder = new Order();
        given(orderProcessingService.processOrder(any(Order.class))).willThrow(new RuntimeException("Error processing order"));

        // When & Then
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Error processing order"));
    }
}
