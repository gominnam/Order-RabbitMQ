package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.TaskStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface TaskStatusService {

    TaskStatus saveTaskStatus(TaskStatus taskStatus);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    TaskStatus updateTaskStatus(Long taskId, String status);
}
