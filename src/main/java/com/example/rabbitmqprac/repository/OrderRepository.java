package com.example.rabbitmqprac.repository;

import com.example.rabbitmqprac.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
