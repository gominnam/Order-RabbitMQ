package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.config.RabbitMQConfig;
import com.example.rabbitmqprac.model.Order;
import com.example.rabbitmqprac.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks //테스트 대상 클래스에 대한 인스턴스를 생성하고 해당 클래스가 의존하는 필드를 자동으로 주입
    private OrderServiceImpl orderService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void createOrderShouldReturnSavedOrder() {
        // given
        Order order = new Order();
        order.setCustomerId("customer-1");
        order.setProduct("product-1");
        order.setAmount(100.0);

        // when
        Order savedOrder = orderService.createOrder(order);
        String orderMessage = String.format("{\"id\":\"%s\",\"customerId\":\"customer-1\",\"product\":\"product-1\",\"amount\":100.0}", savedOrder.getId());
        System.out.print("order message: " + orderMessage);

        // then
        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                orderMessage
        );
    }
}
