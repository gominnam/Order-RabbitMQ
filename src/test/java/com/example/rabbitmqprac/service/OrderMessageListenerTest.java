package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.states.CompletedState;
import com.example.rabbitmqprac.states.FailedState;
import com.example.rabbitmqprac.states.ProcessingState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderMessageListenerTest {

    @Mock
    private TaskStatusService taskStatusService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderMessageListener orderMessageListener;

    private Order order;

    private final String orderMessageJson = "{\"id\":1,\"customerId\":\"minjun\"}";

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1L)
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-21T14:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .totalQuantity(3)
                .totalPrice(4000000)
                .build();
    }

    @Test
    public void receiveOrderMessageWithSuccess() throws Exception {
        // given
        when(objectMapper.readValue(eq(orderMessageJson), eq(Order.class))).thenReturn(order);

        // when
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextFloat()).thenReturn(0.7f); // 0.7보다 작거나 같아서 성공 시나리오
        ReflectionTestUtils.setField(orderMessageListener, "random", mockRandom);

        orderMessageListener.receiveOrderMessage(orderMessageJson);

        // then
        verify(objectMapper).readValue(eq(orderMessageJson), eq(Order.class));
        verify(taskStatusService).updateTaskStatus(anyLong(), eq(ProcessingState.getInstance().toString()));
        verify(taskStatusService).updateTaskStatus(anyLong(), eq(CompletedState.getInstance().toString()));
    }

    @Test
    public void receiveOrderMessageWithFailed() throws Exception {
        // given
        when(objectMapper.readValue(eq(orderMessageJson), eq(Order.class))).thenReturn(order);

        // when
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextFloat()).thenReturn(0.8f); // 0.7보다 크기 때문에 실패 시나리오
        ReflectionTestUtils.setField(orderMessageListener, "random", mockRandom);

        orderMessageListener.receiveOrderMessage(orderMessageJson);

        // then
        verify(objectMapper).readValue(eq(orderMessageJson), eq(Order.class));
        verify(taskStatusService).updateTaskStatus(anyLong(), eq(ProcessingState.getInstance().toString()));
        verify(taskStatusService).updateTaskStatus(anyLong(), eq(FailedState.getInstance().toString()));
    }
}
