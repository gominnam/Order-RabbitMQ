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

import java.util.Random;

@Service
public class OrderMessageListener {

    private final TaskStatusService taskStatusService;
    private final ObjectMapper objectMapper;
    private final Random random;

    @Autowired
    public OrderMessageListener(TaskStatusService taskStatusService, ObjectMapper objectMapper) {
        this.taskStatusService = taskStatusService;
        this.objectMapper = objectMapper;
        this.random = new Random();
    }

    private boolean getRandomBoolean() {
        return random.nextFloat() <= 0.7;
    }

    @Transactional(timeout=600) // todo: @Async 비동기를 활용
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, concurrency = "10-15")
    public void receiveOrderMessage(final String message) throws Exception {
        Order receivedOrder = objectMapper.readValue(message, Order.class);

        taskStatusService.updateTaskStatus(receivedOrder.getId(), ProcessingState.getInstance().toString());

        //장시간 처리가 필요한 작업은 별도의 스레드나 비동기 작업으로 관리하거나, Spring의 @Async 어노테이션을 사용하여 비동기적으로 실행하는 것이 좋음
        // 메시지로 받은 주문 정보를 처리하는 로직을 수행하는 것으로 가정(로깅, 이메일, SMS 등.. )
        Thread.sleep(60000); // 180000밀리초 = 60초(1분)
        boolean randomBoolean = getRandomBoolean();//sleep 서비스에서 서비스가 성공할 확률 70% 로 설정
        System.out.println("Random boolean: " + randomBoolean);

        if(!randomBoolean){
            taskStatusService.updateTaskStatus(receivedOrder.getId(), FailedState.getInstance().toString());
            return;
        }
        taskStatusService.updateTaskStatus(receivedOrder.getId(), CompletedState.getInstance().toString());
    }
}
