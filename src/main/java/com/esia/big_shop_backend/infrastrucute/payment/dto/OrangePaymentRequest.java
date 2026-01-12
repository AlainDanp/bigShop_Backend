package com.esia.big_shop_backend.infrastrucute.payment.dto;

public record OrangePaymentRequest(
        String amount,
        String currency,
        String customerInfo
) {}
