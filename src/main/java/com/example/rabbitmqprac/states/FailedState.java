package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailedState implements TaskStatusState {

    @Getter
    private static final FailedState instance = new FailedState();

    private FailedState() {}

    private static final OrderTaskStatus STATUS = OrderTaskStatus.FAILED;

    private static final Logger logger = LoggerFactory.getLogger(FailedState.class);

    @Override
    public void apply(TaskStatus taskStatus) {
        logger.info("Changing taskStatus from {} to Failed", taskStatus.getStatus());
        taskStatus.setStatus("FAILED");
    }

    @Override
    public TaskStatusState next() {
        return this;
    }

    @Override
    public String toString(){
        return STATUS.toString();
    }
}
