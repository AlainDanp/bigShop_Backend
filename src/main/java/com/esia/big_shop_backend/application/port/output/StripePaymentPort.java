package com.esia.big_shop_backend.application.port.output;

import java.math.BigDecimal;

public interface StripePaymentPort {

    record CreateIntentResult(String paymentIntentId, String clientSecret) {}

    CreateIntentResult createPaymentIntent(BigDecimal amount, String currency, String description);

    void confirmPaymentIntent(String paymentIntentId);

    void refund(String paymentIntentId);

    String getStatus(String paymentIntentId);

    void handleWebhook(String payload, String signatureHeader);
}
