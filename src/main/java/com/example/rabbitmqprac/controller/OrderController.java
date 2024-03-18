package com.example.rabbitmqprac.controller;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        //todo: error handling
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }


}
