package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.model.TaskStatus;

public interface TaskStatusState {
    void apply(TaskStatus taskStatus);

    TaskStatusState next();
}
