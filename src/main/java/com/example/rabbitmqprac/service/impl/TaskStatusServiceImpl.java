package com.example.rabbitmqprac.service.impl;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;
import com.example.rabbitmqprac.repository.TaskStatusRepository;
import com.example.rabbitmqprac.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    public TaskStatus saveTaskStatus(TaskStatus taskStatus) {
        return taskStatusRepository.save(taskStatus);
    }

    @Override
    public TaskStatus updateTaskStatus(Long taskId, OrderTaskStatus status) {//todo: edit this method to update task status
        return null;
    }

    public TaskStatus updateTaskStatus(Long taskId) { //todo: edit this method to update task status
        TaskStatus taskStatus = taskStatusRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        taskStatus.setStatus(OrderTaskStatus.PROCESSING.toString());
        return taskStatusRepository.save(taskStatus);
    }
}
