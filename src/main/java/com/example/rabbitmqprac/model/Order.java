package com.example.rabbitmqprac.model;

import lombok.Data;

@Data
public class Order {
    private String id;
    private String customerId;
    private String product;
    private double amount;
}
