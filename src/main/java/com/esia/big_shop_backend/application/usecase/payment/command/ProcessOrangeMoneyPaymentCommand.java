package com.esia.big_shop_backend.application.usecase.payment.command;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessOrangeMoneyPaymentCommand {
    private String userEmail;
    private Long orderId;

    private BigDecimal amount;
    private String currency;

    private String payerMsisdn;
    private String reference;
}
