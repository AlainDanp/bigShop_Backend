package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.PaymentPort;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentMethod;
import com.esia.big_shop_backend.infrastrucute.payment.dto.OrangePaymentRequest;
import com.esia.big_shop_backend.infrastrucute.payment.mapper.OrangePaymentMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service("orangeMoneyAdapter")
public class OrangeMoneyAdapter implements PaymentPort {

    private final WebClient webClient;

    public OrangeMoneyAdapter(
            @Value("${orange.api.url:https://api.orange.com/orange-money-webpay/dev/v1}") String baseUrl
    ) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public String processPayment(Money amount, PaymentMethod method, String customerInfo) {
        if (method != PaymentMethod.ORANGE_MONEY) {
            throw new IllegalArgumentException("OrangeMoneyAdapter ne supporte que ORANGE_MONEY");
        }

        OrangePaymentRequest req = OrangePaymentMapper.toRequest(amount, customerInfo);
        return "orange_" + UUID.randomUUID();
    }

    @Override
    public boolean verifyPayment(String paymentId) {
        return paymentId != null && paymentId.startsWith("orange_");
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
