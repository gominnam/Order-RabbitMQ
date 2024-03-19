package com.example.rabbitmqprac.service.impl;

import com.example.rabbitmqprac.config.RabbitMQConfig;
import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.repository.OrderRepository;
import com.example.rabbitmqprac.service.OrderService;
import com.example.rabbitmqprac.service.TaskStatusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            System.out.println("Order saved: " + savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }
}
