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
public class OrangeMoneyAdapter implements PaymentPort {

    @Value("${orange.api.key:}")
    private String orangeApiKey;

    @Override
    public String processPayment(Money amount, PaymentMethod method, String customerInfo) {
        // TODO: Integrate with real Orange Money API
        log.info("Processing Orange Money payment: {} {} for customer: {}",
                amount.getAmount(), amount.getCurrency(), customerInfo);

        // Mock payment processing
        String paymentId = "orange_" + UUID.randomUUID().toString();
        log.info("Payment processed successfully with ID: {}", paymentId);
        return paymentId;
    }

    @Override
    public boolean verifyPayment(String paymentId) {
        // TODO: Verify with real Orange API
        log.info("Verifying Orange payment: {}", paymentId);
        return paymentId.startsWith("orange_");
    }

    @Override
    public void refundPayment(String paymentId, Money amount) {
        // TODO: Implement Orange refund
        log.info("Refunding Orange payment: {} with amount: {} {}",
                paymentId, amount.getAmount(), amount.getCurrency());
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        // TODO: Get real status from Orange API
        log.info("Getting Orange payment status: {}", paymentId);
        return "COMPLETED";
    }
}
