package com.esia.big_shop_backend.presentation.dto.response.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;

    private String provider;
    private String providerRef;
    private String status;

    private String clientSecret;
}
