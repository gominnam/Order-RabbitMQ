package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.config.RabbitMQConfig;
import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.states.CompletedState;
import com.example.rabbitmqprac.states.FailedState;
import com.example.rabbitmqprac.states.ProcessingState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderMessageListener {

    private final TaskStatusService taskStatusService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderMessageListener(TaskStatusService taskStatusService, ObjectMapper objectMapper) {
        this.taskStatusService = taskStatusService;
        this.objectMapper = objectMapper;
    }

    @Transactional(timeout=600) // todo: @Async 비동기를 활용
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveOrderMessage(final String message) {
        Order receivedOrder = null;
        try {
            receivedOrder = objectMapper.readValue(message, Order.class);
            System.out.println("Order received: " + receivedOrder);

            taskStatusService.updateTaskStatus(receivedOrder.getId(), ProcessingState.getInstance().toString());

            //장시간 처리가 필요한 작업은 별도의 스레드나 비동기 작업으로 관리하거나, Spring의 @Async 어노테이션을 사용하여 비동기적으로 실행하는 것이 좋음
            try {
                // 메시지로 받은 주문 정보를 처리하는 로직을 수행하는 것으로 가정(로깅, 이메일, SMS 등.. )
                Thread.sleep(180000); // 180000밀리초 = 180초(3분)
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 현재 스레드의 인터럽트 상태를 설정
                Thread.currentThread().interrupt();
            }

            taskStatusService.updateTaskStatus(receivedOrder.getId(), CompletedState.getInstance().toString());
        } catch (Exception e) {
            if(receivedOrder != null){
                taskStatusService.updateTaskStatus(receivedOrder.getId(), FailedState.getInstance().toString());
            }
            e.printStackTrace();
        }
    }
}
