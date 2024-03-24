package com.example.rabbitmqprac.controller;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.response.ApiResponse;
import com.example.rabbitmqprac.service.OrderProcessingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderProcessingService orderProcessingService;

    @Autowired
    public OrderController(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @PostMapping("/api/order")
    public ApiResponse<?> createOrder(@RequestBody Order order) throws JsonProcessingException {
        Order resultOrder = orderProcessingService.processOrder(order);
        return new ApiResponse<>(200, "OK", resultOrder);
    }
}
