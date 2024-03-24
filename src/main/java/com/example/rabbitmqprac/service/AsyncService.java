package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.states.CompletedState;
import com.example.rabbitmqprac.states.FailedState;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class AsyncService {

    private final TaskStatusService taskStatusService;
    private final Random random;

    public AsyncService(TaskStatusService taskStatusService) {
        this.taskStatusService = taskStatusService;
        this.random = new Random();
    }

    private boolean getRandomBoolean() {
        return random.nextFloat() <= 0.7;
    }

    @Async
    public void processOrderAsync(Order order) throws InterruptedException {
        Thread.sleep(60000); // 비동기 처리 예시 (1분 대기)
        updateTaskStatusWithNewTransaction(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTaskStatusWithNewTransaction(Order order) {
        boolean randomBoolean = getRandomBoolean();
        System.out.println("Random boolean: " + randomBoolean);
        if (!randomBoolean) {
            taskStatusService.updateTaskStatus(order.getId(), FailedState.getInstance().toString());
        } else {
            taskStatusService.updateTaskStatus(order.getId(), CompletedState.getInstance().toString());
        }
    }
}
