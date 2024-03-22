package com.example.rabbitmqprac.repository;


import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.model.TaskStatus;
import com.example.rabbitmqprac.states.PendingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TaskStatusRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = Order.builder() // 주문 객체 생성
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
    public void testCreateAndFindTaskStatus() {
        // given
        TaskStatus newTaskStatus = TaskStatus.builder()
                .order(order)
                .status(PendingState.getInstance().toString())
                .build();

        // when
        TaskStatus savedTaskStatus = entityManager.persistFlushFind(newTaskStatus);
        Optional<TaskStatus> foundTaskStatus = taskStatusRepository.findById(savedTaskStatus.getId());

        // then
        assertTrue(foundTaskStatus.isPresent());
        assertThat(foundTaskStatus.get().getId()).isEqualTo(newTaskStatus.getId());
        assertThat(foundTaskStatus.get().getStatus()).isEqualTo(newTaskStatus.getStatus());
    }

}
