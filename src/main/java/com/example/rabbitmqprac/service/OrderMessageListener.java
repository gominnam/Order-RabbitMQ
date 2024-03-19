package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.config.RabbitMQConfig;
import com.example.rabbitmqprac.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveOrderMessage(final String message) {
        try {
            Order order = objectMapper.readValue(message, Order.class);
            // 메시지로 받은 주문 정보를 처리하는 로직(로깅, 이메일, SMS 등)
            System.out.println("Order received: " + order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
