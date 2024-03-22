package com.example.rabbitmqprac.repository;

import com.example.rabbitmqprac.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    Optional<Order> findById(Long aLong);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.id = :orderId")
    Optional<Order> findOrderWithOrderItemsByOrderId(Long orderId);

    @Query("SELECT o FROM Order o JOIN FETCH o.taskStatus WHERE o.id = :orderId")
    Optional<Order> findOrderWithTaskStatusByOrderId(Long orderId);
}
