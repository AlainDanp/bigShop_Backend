package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.StripePaymentPort;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentAdapter implements StripePaymentPort {

    private static final Logger logger = LoggerFactory.getLogger(StripePaymentAdapter.class);

    @Value("${stripe.api.key:}")
    private String stripeApiKey;

    @PostConstruct
    void init() {
        if (stripeApiKey != null && !stripeApiKey.isBlank()) {
            Stripe.apiKey = stripeApiKey;
        } else {
            logger.warn("Stripe API key is not configured. Stripe payments will fail.");
        }
    }

    @Override
    public CreateIntentResult createPaymentIntent(BigDecimal amount, String currency, String description) {
        logger.info("Creating Stripe Payment Intent for amount {} {}", amount, currency);
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // Stripe works with cents
                    .setCurrency(currency.toLowerCase())
                    .setDescription(description)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return new CreateIntentResult(paymentIntent.getId(), paymentIntent.getClientSecret());
        } catch (StripeException e) {
            logger.error("Error creating Stripe Payment Intent", e);
            throw new IllegalStateException("Error creating Stripe Payment Intent: " + e.getMessage(), e);
        }
    }

    @Override
    public void confirmPaymentIntent(String paymentIntentId) {
        logger.info("Confirming Stripe Payment Intent: {}", paymentIntentId);
        // Usually, confirmation happens on the client-side with the client_secret.
        // This method could be used for server-side confirmation if needed.
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            if (!"succeeded".equals(paymentIntent.getStatus())) {
                 PaymentIntent.retrieve(paymentIntentId).confirm();
            }
        } catch (StripeException e) {
            logger.error("Error confirming Stripe Payment Intent {}", paymentIntentId, e);
            throw new IllegalStateException("Error confirming Stripe Payment Intent: " + e.getMessage(), e);
        }
    }

    @Override
    public void refund(String paymentIntentId) {
        logger.info("Refunding Stripe Payment Intent: {}", paymentIntentId);
        // This is a placeholder. You'd need to implement refund logic.
        // com.stripe.param.RefundCreateParams params = ...
        // com.stripe.model.Refund.create(params);
    }

    @Override
    public String getStatus(String paymentIntentId) {
        logger.info("Getting status for Stripe Payment Intent: {}", paymentIntentId);
        try {
            return PaymentIntent.retrieve(paymentIntentId).getStatus();
        } catch (StripeException e) {
            logger.error("Error getting status for Stripe Payment Intent {}", paymentIntentId, e);
            return "unknown";
        }
    }

    @Override
    public void handleWebhook(String payload, String signatureHeader) {
        logger.info("Handling Stripe Webhook");
        // This is a placeholder. You need to implement webhook signature verification
        // and event handling logic here.
        // See Stripe's documentation for `com.stripe.net.Webhook`.
    }
}
