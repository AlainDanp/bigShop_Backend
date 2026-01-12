package com.esia.big_shop_backend.application.usecase.payment.command;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundPaymentCommand {
    private String userEmail;
    private Long paymentId;
}
