package com.example.rabbitmqprac.repository;

import com.example.rabbitmqprac.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest // JPA 테스트를 위한 설정을 로드하고, 테스트용 데이터소스를 제공하는 어노테이션
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateAndFindOrder() {
        // given
        Order newOrder = Order.builder()
                .customerId("minjun")
                .orderDate(LocalDateTime.parse("2024-03-21T14:30:00"))
                .shippingAddress("서울시 강남구 테헤란로 1234")
                .amount(8800)
                .orderItems(Collections.emptyList()) // 실제 테스트 시에는 실제 OrderItem 엔티티를 포함시켜야 함
                .build();

        // when
        Order savedOrder = entityManager.persistFlushFind(newOrder);
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());

        // then
        assertTrue(foundOrder.isPresent());
        assertThat(foundOrder.get().getCustomerId()).isEqualTo(newOrder.getCustomerId());
        assertThat(foundOrder.get().getShippingAddress()).isEqualTo(newOrder.getShippingAddress());
        assertThat(foundOrder.get().getAmount()).isEqualTo(8800);
    }
}
