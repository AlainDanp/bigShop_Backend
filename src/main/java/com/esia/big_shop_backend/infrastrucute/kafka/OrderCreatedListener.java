package com.esia.big_shop_backend.infrastrucute.kafka;

import com.esia.big_shop_backend.application.usecase.order.SendOrderConfirmationEmailUseCase;
import com.esia.big_shop_backend.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedListener {

    private final SendOrderConfirmationEmailUseCase sendOrderConfirmationEmailUseCase;
    private static final String TOPIC_ORDER_CREATED = "order-created-topic";
    private static final String GROUP_ID = "big-shop-group";

    @KafkaListener(topics = TOPIC_ORDER_CREATED, groupId = GROUP_ID)
    public void listen(OrderCreatedEvent event) {
        log.info("Received Kafka message from topic {}: {}", TOPIC_ORDER_CREATED, event);
        sendOrderConfirmationEmailUseCase.execute(event);
    }
}
