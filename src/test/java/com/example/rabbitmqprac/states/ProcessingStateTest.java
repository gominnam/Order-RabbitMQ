package com.example.rabbitmqprac.states;

import com.example.rabbitmqprac.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProcessingStateTest {

    @Mock
    TaskStatus taskStatus;

   @Test
   public void getInstance_ShouldReturnSameInstance() {
       // given
       ProcessingState firstInstance = ProcessingState.getInstance();
       ProcessingState secondInstance = ProcessingState.getInstance();

       // then
       assertSame(firstInstance, secondInstance, "ProcessingState should return the same instance");
   }

   @Test
    public void apply_ShouldChangeStatusToPROCESSING() {
        // given
        ProcessingState.getInstance().apply(taskStatus);

        // then
        verify(taskStatus).setStatus("PROCESSING");
    }

    @Test
    public void next_ShouldThrowUnsupportedOperationException() {
        // when
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            ProcessingState.getInstance().next();
        });

        // then
        String expectedMessage = "Next state needs to be determined by the service layer.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void toString_ShouldReturnPROCESSING() {
        // when
        String result = ProcessingState.getInstance().toString();

        // then
        assertSame("PROCESSING", result, "toString should return PROCESSING");
    }
}
