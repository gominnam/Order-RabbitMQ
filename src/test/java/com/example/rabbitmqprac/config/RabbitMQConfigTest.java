package com.example.rabbitmqprac.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class) //SpringExtension은 Spring의 테스트 관련 기능을 JUnit 5와 통합하는 역할을 합니다.
@SpringBootTest //Spring Boot 애플리케이션의 전체적인 동작을 테스트, 애플리케이션 컨텍스트를 로드하고, 모든 빈을 초기화하며, 필요한 설정을 자동으로 적용
public class RabbitMQConfigTest {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void rabbitAdminBeanExists() {
        assertNotNull(rabbitAdmin, "RabbitAdmin가 null입니다.");
    }
}
