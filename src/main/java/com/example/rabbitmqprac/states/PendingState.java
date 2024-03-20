package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PendingState implements TaskStatusState {

    @Getter
    private static final PendingState instance = new PendingState();

    private PendingState() {}

    private static final OrderTaskStatus STATUS = OrderTaskStatus.PENDING;

    private static final Logger logger = LoggerFactory.getLogger(PendingState.class);

    @Override
    public void apply(TaskStatus taskStatus) {
        logger.info("Changing taskStatus from {} to Pending", taskStatus.getStatus());
        taskStatus.setStatus("PENDING");
    }

    @Override
    public TaskStatusState next() {
        return ProcessingState.getInstance();
    }

    @Override
    public String toString() {
        return STATUS.toString();
    }
}
