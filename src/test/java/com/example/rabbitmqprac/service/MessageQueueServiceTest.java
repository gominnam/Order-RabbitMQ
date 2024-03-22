package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.config.RabbitMQConfig;
import com.example.rabbitmqprac.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageQueueServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MessageQueueService messageQueueService;

    private Order order;

    private final String orderMessageJson = "{\"id\":1,\"customerId\":\"minjun\"}";

    @BeforeEach
    void setUp() {
        // 주문 객체를 초기화하는 코드
        order = Order.builder()
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-21T14:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .totalQuantity(3)
                .totalPrice(4000000)
                .build();
    }

    @Test
    public void sendMessageToQueue() throws JsonProcessingException {
        // given
        when(objectMapper.writeValueAsString(eq(order))).thenReturn(orderMessageJson);

        // when
        messageQueueService.sendMessage(order);

        // then
        verify(rabbitTemplate).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_NAME),
                eq(RabbitMQConfig.ROUTING_KEY),
                eq(orderMessageJson)
        );
    }
}