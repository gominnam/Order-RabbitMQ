package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FailedStateTest {

    @Mock
    TaskStatus taskStatus;

    @Test
    public void getInstance_ShouldReturnSameInstance() {
        // given
        FailedState firstInstance = FailedState.getInstance();
        FailedState secondInstance = FailedState.getInstance();

        // then
        assertSame(firstInstance, secondInstance, "FailedState should return the same instance");
    }

    @Test
    public void apply_ShouldChangeStatusToFAILED() {
        // given
        FailedState.getInstance().apply(taskStatus);

        // then
        verify(taskStatus).setStatus("FAILED");
    }

    @Test
    public void next_ShouldReturnFailedState() {
        // when
        TaskStatusState result = FailedState.getInstance().next();

        // then
        assertSame(FailedState.getInstance(), result, "next should return FailedState");
    }

    @Test
    public void toString_ShouldReturnFAILED() {
        // when
        String result = FailedState.getInstance().toString();

        // then
        assertSame("FAILED", result, "toString should return FAILED");
    }
}
