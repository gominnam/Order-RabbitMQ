package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.states.CompletedState;
import com.example.rabbitmqprac.states.FailedState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AsyncServiceTest {

    @Mock
    private TaskStatusService taskStatusService;

    @Mock
    private Random random;

    @InjectMocks // 해당 객체에 @Mock 또는 @Spy로 생성된 가짜 객체를 주입
    private AsyncService asyncService;

    private Order order;

    @BeforeEach
    void setUp() {
        // given Order Instance
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
    public void testProcessOrderAsync() throws Exception {
        // when
        asyncService.processOrderAsync(order);

        // then
        verify(taskStatusService, times(1)).updateTaskStatus(eq(order.getId()), anyString());
    }

    @Test
    public void testUpdateTaskStatusWithNewTransaction_Completed() {
        // given
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextFloat()).thenReturn(0.7f); // 0.7보다 작거나 같아서 성공 시나리오
        ReflectionTestUtils.setField(asyncService, "random", mockRandom);

        // when
        asyncService.updateTaskStatusWithNewTransaction(order);

        // then
        verify(taskStatusService, times(1)).updateTaskStatus(eq(order.getId()), eq(CompletedState.getInstance().toString()));
    }

    @Test
    public void testUpdateTaskStatusWithNewTransaction_Failed() {
        // given
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextFloat()).thenReturn(0.8f); // 0.7보다 크기 때문에 실패 시나리오
        ReflectionTestUtils.setField(asyncService, "random", mockRandom);

        // when
        asyncService.updateTaskStatusWithNewTransaction(order);

        // then
        verify(taskStatusService, times(1)).updateTaskStatus(eq(order.getId()), eq(FailedState.getInstance().toString()));
    }
}
