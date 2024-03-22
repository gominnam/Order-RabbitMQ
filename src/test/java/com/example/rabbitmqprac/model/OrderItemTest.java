package com.example.rabbitmqprac.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemTest {

    @Test
    void createOrderItemTest(){
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .productId("macbook-pro-2024")
                .quantity(1)
                .unitPrice(1000000)
                .build();

        assertEquals(1L, orderItem.getId(), "주문번호가 일치하지 않습니다.");
        assertEquals("macbook-pro-2024", orderItem.getProductId(), "상품ID가 일치하지 않습니다.");
        assertEquals(1, orderItem.getQuantity(), 0, "수량이 일치하지 않습니다.");
        assertEquals(1000000, orderItem.getUnitPrice(), 0, "금액이 일치하지 않습니다.");
    }

}
