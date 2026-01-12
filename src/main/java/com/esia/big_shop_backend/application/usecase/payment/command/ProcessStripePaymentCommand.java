package com.esia.big_shop_backend.application.usecase.payment.command;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStripePaymentCommand {
    private String userEmail;
    private Long orderId;

    private BigDecimal amount;
    
    @Builder.Default
    private String currency = "EUR";

    private String description;
}
