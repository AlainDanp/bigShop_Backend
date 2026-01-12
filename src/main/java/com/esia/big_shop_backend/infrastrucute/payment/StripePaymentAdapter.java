package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.StripePaymentPort;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StripePaymentAdapter implements StripePaymentPort {

    @Value("${stripe.secret-key:}")
    private String secretKey;

    @PostConstruct
    public void init() {
        if (secretKey != null && !secretKey.isBlank()) {
            Stripe.apiKey = secretKey;
        }
    }

    @Override
    public CreateIntentResult createPaymentIntent(BigDecimal amount, String currency, String description) {
        try {
            long amountInCents = amount.movePointRight(2).longValueExact();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(currency.toLowerCase())
                    .setDescription(description)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                    )
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return new CreateIntentResult(intent.getId(), intent.getClientSecret());
        } catch (StripeException e) {
            throw new IllegalArgumentException("Stripe create intent failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void confirmPaymentIntent(String paymentIntentId) {
    }

    @Override
    public void refund(String paymentIntentId) {
        try {
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(paymentIntentId)
                    .build();
            Refund.create(params);
        } catch (StripeException e) {
            throw new IllegalArgumentException("Stripe refund failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String getStatus(String paymentIntentId) {
        try {
            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
            return intent.getStatus();
        } catch (StripeException e) {
            throw new IllegalArgumentException("Stripe get status failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void handleWebhook(String payload, String signatureHeader) {
    }
}
