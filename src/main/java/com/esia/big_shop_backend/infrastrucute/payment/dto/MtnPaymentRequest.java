package com.esia.big_shop_backend.infrastrucute.payment.dto;

public record MtnPaymentRequest(
        String amount,
        String currency,
        String customerInfo
) {}
