package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.config.RabbitMQConfig;
import com.example.rabbitmqprac.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public Order createOrder(Order order) {
        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);

        try {
            String orderMessage = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, orderMessage);
            System.out.println("Order message sent: " + orderMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return order;
    }
}
