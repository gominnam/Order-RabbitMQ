package com.example.rabbitmqprac.controller;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class) //@WebMvcTest는 내장된 MockMvc 인스턴스를 제공
public class OrderControllerTest {

    @Autowired //Spring의 의존성 주입 기능을 사용하여 빈을 자동으로 연결
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createOrderTest() throws Exception {
        Order order = new Order();
        order.setId("1");
        order.setCustomerId("customer-1");
        order.setProduct("macbook-pro");
        order.setAmount(1);

        given(orderService.createOrder(order)).willReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk());
    }
}
