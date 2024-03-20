package com.example.rabbitmqprac.model;

import com.example.rabbitmqprac.enums.OrderTaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskStatusTest {

    @Test
    public void createTaskStatus() {
        TaskStatus taskStatus = TaskStatus.builder()
                .id(1L)
                .status(OrderTaskStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertEquals(1L, taskStatus.getId(), "Id가 일치하지 않습니다.");
        assertEquals(OrderTaskStatus.PENDING, taskStatus.getStatus(), "상태가 일치하지 않습니다.");
    }
}
