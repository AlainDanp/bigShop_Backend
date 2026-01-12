package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.presentation.dto.response.payment.PaymentResponse;

public class PaymentRestMapper {

    public PaymentResponse toResponse(Payment p, String clientSecret) {
        return PaymentResponse.builder()
                .paymentId(p.getId())
                .orderId(p.getOrderId())
                .provider(p.getProvider())
                .providerRef(p.getProviderRef())
                .status(p.getStatus().name())
                .clientSecret(clientSecret)
                .build();
    }

    public PaymentResponse toResponse(Payment p) {
        return toResponse(p, null);
    }
}
