package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.states.ProcessingState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderMessageListenerTest {

    @Mock
    private TaskStatusService taskStatusService;

    @Mock
    private AsyncService asyncService;

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
    public void receiveOrderMessageTest() throws Exception {
        // given
        when(objectMapper.readValue(eq(orderMessageJson), eq(Order.class))).thenReturn(order);
        doNothing().when(asyncService).processOrderAsync(any(Order.class));

        // when
        orderMessageListener.receiveOrderMessage(orderMessageJson);

        // then
        verify(objectMapper).readValue(eq(orderMessageJson), eq(Order.class));
        verify(taskStatusService).updateTaskStatus(anyLong(), eq(ProcessingState.getInstance().toString()));
        verify(taskStatusService, times(1)).updateTaskStatus(eq(order.getId()), anyString());
        verify(asyncService, times(1)).processOrderAsync(any(Order.class));
    }
}
