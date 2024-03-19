package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.model.TaskStatus;
import com.example.rabbitmqprac.service.impl.MessageQueueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final OrderService orderService;
    private final TaskStatusService taskStatusService;
    private final MessageQueueService messageQueueService;

    @Autowired
    public OrderProcessingService(OrderService orderService, TaskStatusService taskStatusService, MessageQueueService messageQueueService) {
        this.orderService = orderService;
        this.taskStatusService = taskStatusService;
        this.messageQueueService = messageQueueService;
    }

    @Transactional
    public void processOrder(Order order) throws JsonProcessingException {
        // 1. 주문 정보를 데이터베이스에 저장
        orderService.createOrder(order);

        // 2. 주문 처리 상태를 'PENDING'으로 설정하고 데이터베이스에 저장
        TaskStatus taskStatus = TaskStatus.builder().order(order)
                .status(OrderTaskStatus.PENDING)
                .build();

        TaskStatus savedTaskStatus = taskStatusService.saveTaskStatus(taskStatus);

        // 3. 주문 처리 관련 정보를 RabbitMQ를 통해 전송
        messageQueueService.sendMessage(order);

        // 4. 주문 처리 상태를 'PROCESSING'으로 업데이트
        taskStatusService.updateTaskStatus(savedTaskStatus.getId(), OrderTaskStatus.PROCESSING);
    }
}
