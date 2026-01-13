package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendOrderConfirmationEmailUseCase {

    public void execute(OrderCreatedEvent event) {
        // In a real scenario, this would use an EmailService to send an actual email
        log.info("----------------------------------------------------------");
        log.info("PROCESSING ORDER CREATED EVENT - SENDING EMAIL");
        log.info("Order ID: {}", event.getOrderId());
        log.info("User ID: {}", event.getUserId());
        log.info("Total Amount: {}", event.getTotalAmount());
        log.info("Email sent successfully (Simulated)");
        log.info("----------------------------------------------------------");
    }
}
