package com.example.rabbitmqprac.repository;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class OrderItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Order order;

    @BeforeEach
    public void setUp(){
        order = Order.builder()
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-21T14:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .totalQuantity(3)
                .totalPrice(5000000)
                .orderItems(Collections.emptyList())
                .build();
        entityManager.persist(order);
    }

    @Test
    public void testCreateAndFindOrderItem() {
        // given
        OrderItem newOrderItem = OrderItem.builder()
                .productId("macbook-pro-2024")
                .quantity(2)
                .unitPrice(2000000)
                .order(order)
                .build();

        // when
        OrderItem savedOrderItem = entityManager.persistFlushFind(newOrderItem);
        Optional<OrderItem> foundOrderItem = orderItemRepository.findById(savedOrderItem.getId());

        // then
        assertTrue(foundOrderItem.isPresent());
        assertThat(foundOrderItem.get().getId()).isEqualTo(newOrderItem.getId());
        assertThat(foundOrderItem.get().getProductId()).isEqualTo(newOrderItem.getProductId());
        assertThat(foundOrderItem.get().getQuantity()).isEqualTo(newOrderItem.getQuantity());
        assertThat(foundOrderItem.get().getUnitPrice()).isEqualTo(newOrderItem.getUnitPrice());
    }

    @Test
    public void testCreateAndFindMultipleOrderItems(){
        // given
        OrderItem newOrderItem1 = OrderItem.builder()
                .productId("macbook-pro-2024")
                .quantity(2)
                .unitPrice(2000000)
                .order(order)
                .build();

        OrderItem newOrderItem2 = OrderItem.builder()
                .productId("iphone-15")
                .quantity(1)
                .unitPrice(1500000)
                .order(order)
                .build();

        // when
        OrderItem savedOrderItem1 = entityManager.persistFlushFind(newOrderItem1);
        OrderItem savedOrderItem2 = entityManager.persistFlushFind(newOrderItem2);

        Optional<OrderItem> foundOrderItem1 = orderItemRepository.findById(savedOrderItem1.getId());
        Optional<OrderItem> foundOrderItem2 = orderItemRepository.findById(savedOrderItem2.getId());

        // then
        assertTrue(foundOrderItem1.isPresent());
        assertThat(foundOrderItem1.get().getId()).isEqualTo(newOrderItem1.getId());
        assertThat(foundOrderItem1.get().getProductId()).isEqualTo(newOrderItem1.getProductId());
        assertThat(foundOrderItem1.get().getQuantity()).isEqualTo(newOrderItem1.getQuantity());
        assertThat(foundOrderItem1.get().getUnitPrice()).isEqualTo(newOrderItem1.getUnitPrice());

        assertTrue(foundOrderItem2.isPresent());
        assertThat(foundOrderItem2.get().getId()).isEqualTo(newOrderItem2.getId());
        assertThat(foundOrderItem2.get().getProductId()).isEqualTo(newOrderItem2.getProductId());
        assertThat(foundOrderItem2.get().getQuantity()).isEqualTo(newOrderItem2.getQuantity());
        assertThat(foundOrderItem2.get().getUnitPrice()).isEqualTo(newOrderItem2.getUnitPrice());
    }
}
