package com.example.rabbitmqprac.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RabbitMQConfigTest {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void rabbitAdminBeanExists() {
        assertNotNull(rabbitAdmin, "RabbitAdmin가 null입니다.");
    }
}
