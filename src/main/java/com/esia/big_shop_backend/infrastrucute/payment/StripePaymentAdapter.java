package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.PaymentPort;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class StripePaymentAdapter implements PaymentPort {

    @Value("${stripe.api.key:}")
    private String stripeApiKey;

    @Override
    public String processPayment(Money amount, PaymentMethod method, String customerInfo) {
        // TODO: Integrate with real Stripe API
        log.info("Processing Stripe payment: {} {} for customer: {}",
                amount.getAmount(), amount.getCurrency(), customerInfo);

        // Mock payment processing
        String paymentId = "stripe_" + UUID.randomUUID().toString();
        log.info("Payment processed successfully with ID: {}", paymentId);
        return paymentId;
    }

    @Override
    public boolean verifyPayment(String paymentId) {
        // TODO: Verify with real Stripe API
        log.info("Verifying Stripe payment: {}", paymentId);
        return paymentId.startsWith("stripe_");
    }

    @Override
    public void refundPayment(String paymentId, Money amount) {
        // TODO: Implement Stripe refund
        log.info("Refunding Stripe payment: {} with amount: {} {}",
                paymentId, amount.getAmount(), amount.getCurrency());
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        // TODO: Get real status from Stripe API
        log.info("Getting Stripe payment status: {}", paymentId);
        return "COMPLETED";
    }
}
