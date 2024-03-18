package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.Order;

public interface OrderService {

    Order createOrder(Order order);
}
