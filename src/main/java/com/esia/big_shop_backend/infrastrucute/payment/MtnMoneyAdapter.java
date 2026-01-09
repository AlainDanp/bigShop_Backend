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
public class MtnMoneyAdapter implements PaymentPort {

    @Value("${mtn.api.key:}")
    private String mtnApiKey;

    @Override
    public String processPayment(Money amount, PaymentMethod method, String customerInfo) {
        // TODO: Integrate with real MTN Mobile Money API
        log.info("Processing MTN Mobile Money payment: {} {} for customer: {}",
                amount.getAmount(), amount.getCurrency(), customerInfo);

        // Mock payment processing
        String paymentId = "mtn_" + UUID.randomUUID().toString();
        log.info("Payment processed successfully with ID: {}", paymentId);
        return paymentId;
    }

    @Override
    public boolean verifyPayment(String paymentId) {
        // TODO: Verify with real MTN API
        log.info("Verifying MTN payment: {}", paymentId);
        return paymentId.startsWith("mtn_");
    }

    @Override
    public void refundPayment(String paymentId, Money amount) {
        // TODO: Implement MTN refund
        log.info("Refunding MTN payment: {} with amount: {} {}",
                paymentId, amount.getAmount(), amount.getCurrency());
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        // TODO: Get real status from MTN API
        log.info("Getting MTN payment status: {}", paymentId);
        return "COMPLETED";
    }
}
