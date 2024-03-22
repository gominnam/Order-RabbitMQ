package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderProcessingServiceTest {

    @Mock
    MessageQueueService messageQueueService;

    @Mock
    OrderService orderService;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    OrderProcessingService orderProcessingService;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = Order.builder()
                .customerId("test-customer")
                .shippingAddress("test-address")
                .totalPrice(1000000)
                .totalQuantity(1)
                .build();
    }

    @Test
    public void processOrderTest() throws JsonProcessingException {
        // 가정 설정
        when(orderService.createOrder(any(Order.class))).thenReturn(order);
        doNothing().when(messageQueueService).sendMessage(any(Order.class));

        // 메서드 실행
        Order processedOrder = orderProcessingService.processOrder(order);

        // 결과 검증
        assertEquals(order, processedOrder);
        verify(orderService, times(1)).createOrder(any(Order.class)); // time(1): 1번 호출됐는지 확인
        verify(messageQueueService, times(1)).sendMessage(any(Order.class));
    }
}
