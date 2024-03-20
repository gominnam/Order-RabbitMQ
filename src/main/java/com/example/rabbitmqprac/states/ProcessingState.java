package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingState implements TaskStatusState {

    @Getter
    private static final ProcessingState instance = new ProcessingState();

    private ProcessingState() {}

    private static final OrderTaskStatus STATUS = OrderTaskStatus.PROCESSING;

    private static final Logger logger = LoggerFactory.getLogger(PendingState.class);

    @Override
    public void apply(TaskStatus taskStatus) {
        logger.info("Changing taskStatus from {} to Processing", taskStatus.getStatus());
        taskStatus.setStatus("PROCESSING");
    }

    @Override
    public TaskStatusState next() {
        throw new UnsupportedOperationException("Next state needs to be determined by the service layer.");
    }

    @Override
    public String toString() {
        return STATUS.toString();
    }
}
