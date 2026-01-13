package com.esia.big_shop_backend.infrastrucute.kafka;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.domain.event.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final org.springframework.kafka.core.KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String TOPIC_ORDER_CREATED = "order-created-topic";
    private static final String TOPIC_PAYMENT_PROCESSED = "payment-processed-topic";
    private static final String TOPIC_CART_UPDATED = "cart-updated-topic";
    
    private static final String TOPIC_USER_REGISTERED = "user-registered-topic";
    private static final String TOPIC_USER_DELETED = "user-deleted-topic";
    
    private static final String TOPIC_PRODUCT_CREATED = "product-created-topic";
    private static final String TOPIC_PRODUCT_UPDATED = "product-updated-topic";
    private static final String TOPIC_PRODUCT_DELETED = "product-deleted-topic";
    
    private static final String TOPIC_CATEGORY_CREATED = "category-created-topic";
    private static final String TOPIC_CATEGORY_UPDATED = "category-updated-topic";
    private static final String TOPIC_CATEGORY_DELETED = "category-deleted-topic";
    
    private static final String TOPIC_ORDER_CANCELLED = "order-cancelled-topic";
    private static final String TOPIC_ORDER_SHIPPED = "order-shipped-topic";
    private static final String TOPIC_ORDER_DELIVERED = "order-delivered-topic";

    private void sendEvent(String topic, String key, Object event) {
        log.info("Publishing event to topic {}: {}", topic, event);
        kafkaTemplate.send(topic, key, event);
    }

    @Override
    public void publish(OrderCreatedEvent event) {
        sendEvent(TOPIC_ORDER_CREATED, String.valueOf(event.getOrderId()), event);
    }

    @Override
    public void publish(PaymentProcessedEvent event) {
        sendEvent(TOPIC_PAYMENT_PROCESSED, String.valueOf(event.getPaymentId()), event);
    }

    @Override
    public void publish(CartUpdatedEvent event) {
        sendEvent(TOPIC_CART_UPDATED, String.valueOf(event.getCartId()), event);
    }

    @Override
    public void publish(UserRegisteredEvent event) {
        sendEvent(TOPIC_USER_REGISTERED, String.valueOf(event.getUserId()), event);
    }

    @Override
    public void publish(UserDeletedEvent event) {
        sendEvent(TOPIC_USER_DELETED, String.valueOf(event.getUserId()), event);
    }

    @Override
    public void publish(ProductCreatedEvent event) {
        sendEvent(TOPIC_PRODUCT_CREATED, String.valueOf(event.getProductId()), event);
    }

    @Override
    public void publish(ProductUpdatedEvent event) {
        sendEvent(TOPIC_PRODUCT_UPDATED, String.valueOf(event.getProductId()), event);
    }

    @Override
    public void publish(ProductDeletedEvent event) {
        sendEvent(TOPIC_PRODUCT_DELETED, String.valueOf(event.getProductId()), event);
    }

    @Override
    public void publish(CategoryCreatedEvent event) {
        sendEvent(TOPIC_CATEGORY_CREATED, String.valueOf(event.getCategoryId()), event);
    }

    @Override
    public void publish(CategoryUpdatedEvent event) {
        sendEvent(TOPIC_CATEGORY_UPDATED, String.valueOf(event.getCategoryId()), event);
    }

    @Override
    public void publish(CategoryDeletedEvent event) {
        sendEvent(TOPIC_CATEGORY_DELETED, String.valueOf(event.getCategoryId()), event);
    }

    @Override
    public void publish(OrderCancelledEvent event) {
        sendEvent(TOPIC_ORDER_CANCELLED, String.valueOf(event.getOrderId()), event);
    }

    @Override
    public void publish(OrderShippedEvent event) {
        sendEvent(TOPIC_ORDER_SHIPPED, String.valueOf(event.getOrderId()), event);
    }

    @Override
    public void publish(OrderDeliveredEvent event) {
        sendEvent(TOPIC_ORDER_DELIVERED, String.valueOf(event.getOrderId()), event);
    }
}
