package com.example.rabbitmqprac.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void createOrder() {
        Order order = Order.builder()
                .id(1L)
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-21T14:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .totalQuantity(3)
                .totalPrice(5000000)
                .orderItems(Collections.emptyList())
                .build();

        assertEquals(1L, order.getId(), "주문번호가 일치하지 않습니다.");
        assertEquals("minjun", order.getCustomerId(), "고객ID가 일치하지 않습니다.");
        assertEquals(3, order.getTotalQuantity(), 0, "수량이 일치하지 않습니다.");
        assertEquals(5000000, order.getTotalPrice(), 0, "금액이 일치하지 않습니다.");
    }
}
