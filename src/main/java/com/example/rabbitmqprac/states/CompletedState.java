package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletedState implements TaskStatusState {

    @Getter
    private static final CompletedState instance = new CompletedState();

    private CompletedState() {}

    private static final OrderTaskStatus STATUS = OrderTaskStatus.COMPLETED;

    private static final Logger logger = LoggerFactory.getLogger(CompletedState.class);

    @Override
    public void apply(TaskStatus taskStatus){
        logger.info("Changing taskStatus from {} to Completed", taskStatus.getStatus());
        taskStatus.setStatus("COMPLETED");
    }

    @Override
    public TaskStatusState next(){
        return this;
    }

    @Override
    public String toString() {
        return STATUS.toString();
    }
}
