package com.example.rabbitmqprac.service;

import com.example.rabbitmqprac.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.mockito.Mockito.*;

@JsonTest //JSON 직렬화와 역직렬화에 대한 테스트를 지원
public class OrderMessageListenerTest {

    @InjectMocks
    private OrderMessageListener orderMessageListener;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendMessageToQueue() throws Exception {

        String jsonMessage = "{\"id\":\"d04fc4ca-3c52-463c-9a50-b2813d2e606d\",\"customerId\":\"customer-1\",\"product\":\"product-1\",\"amount\":100.0}";

        Order order = objectMapper.readValue(jsonMessage, Order.class);
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        when(objectMapperMock.readValue(jsonMessage, Order.class)).thenReturn(order);

        // 메시지 수신 메서드 테스트
        orderMessageListener.receiveOrderMessage(jsonMessage);

        // 주문 처리 로직 (예: 주문 저장, 이메일 전송 등)이 올바르게 호출되었는지 검증하는 코드를 추가합니다.
        // 예: verify(orderRepository, times(1)).save(any(Order.class));
    }


}
