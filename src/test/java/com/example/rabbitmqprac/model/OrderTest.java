package com.example.rabbitmqprac.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void createOrder() {
        Order order = new Order();
        order.setId("1");
        order.setCustomerId("customer-1");
        order.setProduct("project-1");
        order.setAmount(100.0);

        assertEquals("1", order.getId(), "주문번호가 일치하지 않습니다.");
        assertEquals("customer-1", order.getCustomerId(), "고객번호가 일치하지 않습니다.");
        assertEquals("project-1", order.getProduct(), "상품명이 일치하지 않습니다.");
        assertEquals(100.0, order.getAmount(), 0.0, "금액이 일치하지 않습니다.");
    }
}
