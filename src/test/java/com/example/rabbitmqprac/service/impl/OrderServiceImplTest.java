package com.example.rabbitmqprac.service.impl;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @Mock //Mockito가 제공하는 가짜 객체 (mock object)를 생성
    private OrderRepository orderRepository;

    @InjectMocks //@Mock 또는 @Spy 어노테이션으로 생성된 모든 가짜 객체를 해당 클래스의 인스턴스에 주입
    private OrderServiceImpl orderService;

    @BeforeEach //각 테스트 메소드가 실행되기 전에 매번 실행
    public void setUp() {
        MockitoAnnotations.initMocks(this); //Mockito 어노테이션 (@Mock, @Spy, @InjectMocks 등)이 붙은 필드를 초기화
    }

    private Order createOrder(Long id, String customerId, String product, int amount) {
        return Order.builder()
                .id(id)
                .customerId(customerId)
                .product(product)
                .amount(amount)
                .build();
    }

    @Test
    void whenCreateOrder_thenReturnOrder() {
        // given
        Order inputOrder = createOrder(1L, "customer-1", "macbook-pro", 1);
        Order savedOrder = createOrder(1L, "customer-1", "macbook-pro", 1);

        // when
        when(orderRepository.save(inputOrder)).thenReturn(savedOrder);

        // then
        Order result = orderService.createOrder(inputOrder);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerId()).isEqualTo("customer-1");
        assertThat(result.getProduct()).isEqualTo("macbook-pro");
        assertThat(result.getAmount()).isEqualTo(1);
    }
}
