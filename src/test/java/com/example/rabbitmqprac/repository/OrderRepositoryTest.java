package com.example.rabbitmqprac.repository;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.model.OrderItem;
import com.example.rabbitmqprac.model.TaskStatus;
import com.example.rabbitmqprac.states.PendingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest // JPA 테스트를 위한 설정을 로드하고, 테스트용 데이터소스를 제공하는 어노테이션
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    public void setUp(){
        order = Order.builder()
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-21T14:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .totalQuantity(3)
                .totalPrice(4000000)
                .build();

        OrderItem item1 = OrderItem.builder()
                .productId("macbook-pro-2024")
                .quantity(1)
                .unitPrice(2000000)
                .order(order)
                .build();

        OrderItem item2 = OrderItem.builder()
                .productId("iphone-13")
                .quantity(2)
                .unitPrice(1000000)
                .order(order)
                .build();

        order.setOrderItems(Arrays.asList(item1, item2));

        TaskStatus status = TaskStatus.builder()
                .order(order)
                .status(PendingState.getInstance().toString())
                .build();

        order.setTaskStatus(status);

        entityManager.persistFlushFind(order);
        entityManager.clear();
    }

    @Test
    public void testCreateAndFindOrder() {
        // given
        Order newOrder = Order.builder()
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-22T11:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .totalQuantity(1)
                .totalPrice(2000000)
                .orderItems(Collections.emptyList()) // 실제 테스트 시에는 실제 OrderItem 엔티티를 포함시켜야 함
                .build();

        // when
        Order savedOrder = entityManager.persistFlushFind(newOrder);
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());

        // then
        assertTrue(foundOrder.isPresent());
        assertThat(foundOrder.get().getCustomerId()).isEqualTo(newOrder.getCustomerId());
        assertThat(foundOrder.get().getShippingAddress()).isEqualTo(newOrder.getShippingAddress());
        assertThat(foundOrder.get().getTotalQuantity()).isEqualTo(1);
        assertThat(foundOrder.get().getTotalPrice()).isEqualTo(2000000);
    }

    @Test
    public void testFindOrderWithOrderItems(){
        // given
        Long orderId = order.getId();

        //when
        Optional<Order> foundOrder = orderRepository.findOrderWithOrderItemsByOrderId(orderId);

        // then
        assertTrue(foundOrder.isPresent());
        assertThat(foundOrder.get().getOrderItems().size()).isEqualTo(2);
    }

    @Test
    public void testFindOrderWithTaskStatus(){
        // given
        Long orderId = order.getId();

        //when
        Optional<Order> foundOrder = orderRepository.findOrderWithTaskStatusByOrderId(orderId);

        // then
        assertTrue(foundOrder.isPresent());
        assertThat(foundOrder.get().getTaskStatus()).isNotNull();
        assertEquals(foundOrder.get().getTaskStatus().getStatus(), PendingState.getInstance().toString());
    }
}
