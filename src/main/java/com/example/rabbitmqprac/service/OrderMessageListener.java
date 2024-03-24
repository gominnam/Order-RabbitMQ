package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.config.RabbitMQConfig;
import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.states.ProcessingState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderMessageListener {

    private final TaskStatusService taskStatusService;
    private AsyncService asyncService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderMessageListener(TaskStatusService taskStatusService, AsyncService asyncService, ObjectMapper objectMapper) {
        this.taskStatusService = taskStatusService;
        this.asyncService = asyncService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, concurrency = "10-15")
    public void receiveOrderMessage(final String message) throws Exception {
        Order receivedOrder = objectMapper.readValue(message, Order.class);
        taskStatusService.updateTaskStatus(receivedOrder.getId(), ProcessingState.getInstance().toString());

        // 비동기로 처리(1분 소요)
        // 메시지로 받은 주문 정보를 처리하는 로직을 수행하는 것으로 가정(로깅, 이메일, SMS 등.. )
        asyncService.processOrderAsync(receivedOrder);
    }
}
