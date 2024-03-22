package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.model.TaskStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final OrderService orderService;
    private final MessageQueueService messageQueueService;

    @Autowired
    public OrderProcessingService(OrderService orderService, MessageQueueService messageQueueService) {
        this.orderService = orderService;
        this.messageQueueService = messageQueueService;
    }

    @Transactional // RollBack 방지하는 방법 ex) @Transactional(noRollbackFor = {SomeException.class})
    public Order processOrder(Order order) throws JsonProcessingException {
        // 1. 주문, 주문상품, 주문상태를 데이터베이스에 저장(종속관계 설정)
        order.setTaskStatus(TaskStatus.builder().order(order).status(OrderTaskStatus.PENDING.toString()).build());
        Order savedIOrder = orderService.createOrder(order);

        // 2. 주문 처리 관련 정보를 RabbitMQ를 통해 전송
        messageQueueService.sendMessage(order);

        return savedIOrder;
    }
}
