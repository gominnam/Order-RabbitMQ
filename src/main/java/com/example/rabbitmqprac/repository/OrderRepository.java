package com.example.rabbitmqprac.repository;

import com.example.rabbitmqprac.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    Optional<Order> findById(Long aLong);
}
