package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;

public interface TaskStatusService {

    TaskStatus saveTaskStatus(TaskStatus taskStatus);

    TaskStatus updateTaskStatus(Long taskId, OrderTaskStatus status);
}
