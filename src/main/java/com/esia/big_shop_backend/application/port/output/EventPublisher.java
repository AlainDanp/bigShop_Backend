package com.esia.big_shop_backend.application.port.output;

import com.esia.big_shop_backend.domain.event.*;

public interface EventPublisher {
    void publish(OrderCreatedEvent event);
    void publish(PaymentProcessedEvent event);
    void publish(CartUpdatedEvent event);
    
    void publish(UserRegisteredEvent event);
    void publish(UserDeletedEvent event);
    
    void publish(ProductCreatedEvent event);
    void publish(ProductUpdatedEvent event);
    void publish(ProductDeletedEvent event);
    
    void publish(CategoryCreatedEvent event);
    void publish(CategoryUpdatedEvent event);
    void publish(CategoryDeletedEvent event);
    
    void publish(OrderCancelledEvent event);
    void publish(OrderShippedEvent event);
    void publish(OrderDeliveredEvent event);
}
