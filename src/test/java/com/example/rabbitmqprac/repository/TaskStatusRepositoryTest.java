package com.example.rabbitmqprac.repository;


import com.example.rabbitmqprac.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepositoryTest extends JpaRepository<TaskStatus, Long> {

}
