package com.esia.big_shop_backend.infrastrucute.payment.dto;

public record StripePaymentRequest(
        long amountInSmallestUnit,
        String currency,
        String description) {}
