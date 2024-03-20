package com.example.rabbitmqprac.service.impl;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import com.example.rabbitmqprac.model.TaskStatus;
import com.example.rabbitmqprac.repository.TaskStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class TaskStatusServiceImplTest {

    @Mock
    private TaskStatusRepository taskStatusRepository;

    @InjectMocks
    private TaskStatusServiceImpl taskStatusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private TaskStatus createTaskStatus(Long id, OrderTaskStatus status) {
        return TaskStatus.builder()
                .id(id)
                .status(status)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void whenCreateTaskStatus_thenReturnTaskStatus() {
        // given
        TaskStatus inputTaskStatus = createTaskStatus(1L, OrderTaskStatus.PENDING);
        TaskStatus savedTaskStatus = createTaskStatus(1L, OrderTaskStatus.PENDING);

        // when
        when(taskStatusRepository.save(inputTaskStatus)).thenReturn(savedTaskStatus);

        // then
        TaskStatus result = taskStatusService.saveTaskStatus(inputTaskStatus);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(OrderTaskStatus.PENDING);
    }

    @Test
    public void whenUpdateTaskStatus_thenReturnTaskStatus() {
        // given
        TaskStatus inputTaskStatus = createTaskStatus(1L, OrderTaskStatus.PENDING);
        TaskStatus updatedTaskStatus = createTaskStatus(1L, OrderTaskStatus.PROCESSING);

        // when
        when(taskStatusRepository.findById(1L)).thenReturn(java.util.Optional.of(inputTaskStatus)); // Optional.of() 메소드는 주어진 non-null 값을 갖는 Optional 객체를 반환
        when(taskStatusRepository.save(inputTaskStatus)).thenReturn(updatedTaskStatus);

        // then
        TaskStatus result = taskStatusService.updateTaskStatus(1L, OrderTaskStatus.PROCESSING);
    }

    @Test
    public void whenUpdateTaskStatus_thenThrowIllegalArgumentException() {
        // given
        TaskStatus taskStatus = createTaskStatus(1L, OrderTaskStatus.PROCESSING);

        // when
        when(taskStatusRepository.findById(1L)).thenReturn(java.util.Optional.empty()); // Optional.empty() 메소드는 비어있는 Optional 객체를 반환

        // then
        try {
            TaskStatus updatedTaskStatus = taskStatusService.updateTaskStatus(1L, OrderTaskStatus.PROCESSING);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Task not found");
        }
    }
}
