package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // Mockito를 초기화하고, @Mock 어노테이션이 붙은 필드를 자동으로 초기화
public class PendingStateTest {

    @Mock
    TaskStatus taskStatus;

    @Test
    public void getInstances_ShouldReturnSameInstance() {
        // given
        PendingState firstInstance = PendingState.getInstance();
        PendingState secondInstance = PendingState.getInstance();

        // then
        assertSame(firstInstance, secondInstance, "ProcessingState should return the same instance");
    }

    @Test
    public void apply_ShouldChangeStatusToPENDING() {
        // given
        PendingState.getInstance().apply(taskStatus);

        // then
        verify(taskStatus).setStatus("PENDING");
    }

    @Test
    public void next_ShouldReturnProcessingState() {
        // when
        TaskStatusState result = PendingState.getInstance().next();

        // then
        assertSame(ProcessingState.getInstance(), result, "next should return ProcessingState");
    }

    @Test
    public void toString_ShouldReturnPENDING() {
        // when
        String result = PendingState.getInstance().toString();

        // then
        assertSame("PENDING", result, "toString should return PENDING");
    }
}
