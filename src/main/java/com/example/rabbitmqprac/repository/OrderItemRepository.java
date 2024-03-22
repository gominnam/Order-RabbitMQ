package com.example.rabbitmqprac.repository;

import com.example.rabbitmqprac.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Override
    Optional<OrderItem> findById(Long aLong);
}
