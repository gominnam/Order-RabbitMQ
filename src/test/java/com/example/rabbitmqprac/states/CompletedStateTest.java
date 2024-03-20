package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CompletedStateTest {

    @Mock
    TaskStatus taskStatus;

    @Test
    public void getInstance_ShouldReturnSameInstance() {
        // given
        CompletedState firstInstance = CompletedState.getInstance();
        CompletedState secondInstance = CompletedState.getInstance();

        // then
        assertSame(firstInstance, secondInstance, "CompletedState should return the same instance");
    }

    @Test
    public void apply_ShouldChangeStatusToCOMPLETED() {
        // given
        CompletedState.getInstance().apply(taskStatus);

        // then
        verify(taskStatus).setStatus("COMPLETED");
    }

    @Test
    public void next_ShouldReturnCompletedState() {
        // when
        TaskStatusState result = CompletedState.getInstance().next();

        // then
        assertSame(CompletedState.getInstance(), result, "next should return CompletedState");
    }

    @Test
    public void toString_ShouldReturnCOMPLETED() {
        // when
        String result = CompletedState.getInstance().toString();

        // then
        assertSame("COMPLETED", result, "toString should return COMPLETED");
    }
}
