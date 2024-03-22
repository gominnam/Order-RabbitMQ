package com.example.rabbitmqprac.service.impl;

import com.example.rabbitmqprac.model.TaskStatus;
import com.example.rabbitmqprac.repository.TaskStatusRepository;
import com.example.rabbitmqprac.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 데이터를 저장하는 작업을 별도의 트랜잭션으로 분리하여 즉시 커밋
    public TaskStatus updateTaskStatus(Long taskId, String status) {
        try {
            TaskStatus taskStatus = taskStatusRepository.findById(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("TaskStatus not found"));
            taskStatus.setStatus(status);
            System.out.println("before updateTaskStatus save status: " + status);
            return taskStatusRepository.save(taskStatus);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 예외를 다시 던져 상위로 전파
        }
    }
}
