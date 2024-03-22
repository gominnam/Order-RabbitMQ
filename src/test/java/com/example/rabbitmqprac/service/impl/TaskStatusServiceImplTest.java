package com.example.rabbitmqprac.service.impl;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;
import com.example.rabbitmqprac.repository.TaskStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskStatusServiceImplTest {

    @Mock
    private TaskStatusRepository taskStatusRepository;

    @InjectMocks
    private TaskStatusServiceImpl taskStatusService;

    private TaskStatus taskStatus;

    @BeforeEach
    void setUp() {
        taskStatus = TaskStatus.builder()
                .id(1L)
                .status(OrderTaskStatus.PENDING.toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void whenCreateTaskStatus_thenReturnTaskStatus() {
        // given
        TaskStatus inputTaskStatus = taskStatus;
        TaskStatus savedTaskStatus = taskStatus;

        // when
        when(taskStatusRepository.save(inputTaskStatus)).thenReturn(savedTaskStatus);

        // then
        TaskStatus result = taskStatusService.saveTaskStatus(inputTaskStatus);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(OrderTaskStatus.PENDING.toString());
    }

    @Test
    public void whenUpdateTaskStatus_thenReturnTaskStatus() {//todo: after edit updateTaskStatus editing this test
        // given
        Long taskId = 1L;
        String newStatus = OrderTaskStatus.PROCESSING.toString();
        when(taskStatusRepository.findById(taskId)).thenReturn(Optional.of(taskStatus)); // Optional.of() 메소드는 주어진 non-null 값을 갖는 Optional 객체를 반환
        when(taskStatusRepository.save(any(TaskStatus.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));// getArgument() 메소드는 목 객체에 전달된 인수를 반환, 숫자 0은 첫번째 인자를 가리킨다.

        // when
        TaskStatus updatedTaskStatus = taskStatusService.updateTaskStatus(taskId, newStatus);

        // then
        assertEquals(OrderTaskStatus.PROCESSING.toString(), updatedTaskStatus.getStatus());
        verify(taskStatusRepository).save(any(TaskStatus.class));
    }

    @Test
    public void whenUpdateTaskStatus_thenThrowIllegalArgumentException() {
        when(taskStatusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            taskStatusService.updateTaskStatus(1L, "PROCESSING");
        }, "TaskStatus not found");
    }
}
