package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.PaymentPort;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentMethod;
import com.esia.big_shop_backend.infrastrucute.payment.dto.MtnPaymentRequest;
import com.esia.big_shop_backend.infrastrucute.payment.mapper.MtnPaymentMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service("mtnMoneyAdapter")
public class MtnMoneyAdapter implements PaymentPort {

    private final WebClient webClient;

    public MtnMoneyAdapter(
            @Value("${mtn.api.url:https://sandbox.momodeveloper.mtn.com}") String baseUrl
    ) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public String processPayment(Money amount, PaymentMethod method, String customerInfo) {
        if (method != PaymentMethod.MTN_MOBILE_MONEY) {
            throw new IllegalArgumentException("MtnMoneyAdapter ne supporte que MTN_MOBILE_MONEY");
        }

        MtnPaymentRequest req = MtnPaymentMapper.toRequest(amount, customerInfo);

        return "mtn_" + UUID.randomUUID();
    }

    @Override
    public boolean verifyPayment(String paymentId) {
        return paymentId != null && paymentId.startsWith("mtn_");
    }

    @Override
    public void refundPayment(String paymentId, Money amount) {
        if (paymentId == null) {
            throw new IllegalArgumentException("paymentId null");
        }
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        return verifyPayment(paymentId) ? "pending" : "unknown";
    }
}
