package com.example.rabbitmqprac.service.impl;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock //Mockito가 제공하는 가짜 객체 (mock object)를 생성
    private OrderRepository orderRepository;

    @InjectMocks //@Mock 또는 @Spy 어노테이션으로 생성된 모든 가짜 객체를 해당 클래스의 인스턴스에 주입
    private OrderServiceImpl orderService;

    private Order order;
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
    void whenCreateOrder_thenReturnOrder() {
        // given
        Order inputOrder = order;
        Order savedOrder = order;

        // when
        when(orderRepository.save(inputOrder)).thenReturn(savedOrder);

        // then
        Order result = orderService.createOrder(inputOrder);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerId()).isEqualTo("minjun");
        assertThat(result.getTotalQuantity()).isEqualTo(3);
        assertThat(result.getTotalPrice()).isEqualTo(4000000);
    }
}
