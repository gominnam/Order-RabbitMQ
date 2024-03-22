package com.example.rabbitmqprac.repository;


import com.example.rabbitmqprac.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    @Override
    Optional<TaskStatus> findById(Long aLong);
}
