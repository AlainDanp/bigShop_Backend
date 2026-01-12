package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.PaymentPort;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentMethod;
import com.esia.big_shop_backend.infrastrucute.payment.mapper.StripePaymentMapper;
import com.esia.big_shop_backend.infrastrucute.payment.dto.StripePaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("stripePaymentAdapter")
public class StripePaymentAdapter implements PaymentPort {

    @Value("${stripe.api.key:}")
    private String stripeApiKey;

    @PostConstruct
    void init() {
        if (stripeApiKey != null && !stripeApiKey.isBlank()) {
            Stripe.apiKey = stripeApiKey;
        }
    }

    @Override
    public String processPayment(Money amount, PaymentMethod method, String customerInfo) {
        if (method != PaymentMethod.STRIPE) {
            throw new IllegalArgumentException("StripePaymentAdapter ne supporte que STRIPE");
        }

        StripePaymentRequest req = StripePaymentMapper.toRequest(amount, customerInfo);

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(req.amountInSmallestUnit())
                    .setCurrency(req.currency())
                    .putMetadata("customerInfo", req.customerInfo() == null ? "" : req.customerInfo())
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getId();
        } catch (StripeException e) {
            throw new IllegalStateException("Erreur Stripe: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean verifyPayment(String paymentId) {
        try {
            PaymentIntent intent = PaymentIntent.retrieve(paymentId);
            return "succeeded".equalsIgnoreCase(intent.getStatus());
        } catch (StripeException e) {
            return false;
        }
    }

    @Override
    public void refundPayment(String paymentId, Money amount) {
        try {
            long refundAmount = StripePaymentMapper.toRequest(amount, "").amountInSmallestUnit();

            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(paymentId)
                    .setAmount(refundAmount > 0 ? refundAmount : null) // null = refund total
                    .build();

            Refund.create(params);
        } catch (StripeException e) {
            throw new IllegalStateException("Erreur refund Stripe: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        try {
            PaymentIntent intent = PaymentIntent.retrieve(paymentId);
            return intent.getStatus();
        } catch (StripeException e) {
            return "unknown";
        }
    }
}
