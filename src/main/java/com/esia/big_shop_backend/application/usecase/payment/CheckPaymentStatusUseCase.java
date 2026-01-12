package com.esia.big_shop_backend.application.usecase.payment;

import com.esia.big_shop_backend.application.port.output.MtnPaymentPort;
import com.esia.big_shop_backend.application.port.output.OrangeMoneyPaymentPort;
import com.esia.big_shop_backend.application.port.output.StripePaymentPort;
import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentStatus;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class CheckPaymentStatusUseCase {

    private final PaymentRepository paymentRepository;
    private final StripePaymentPort stripePaymentPort;
    private final MtnPaymentPort mtnPaymentPort;
    private final OrangeMoneyPaymentPort orangeMoneyPaymentPort;

    public Payment execute(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for order"));

        String rawStatus = switch (payment.getProvider()) {
            case "STRIPE" -> stripePaymentPort.getStatus(payment.getProviderRef());
            case "MTN" -> mtnPaymentPort.getStatus(payment.getProviderRef());
            case "ORANGE_MONEY" -> orangeMoneyPaymentPort.getStatus(payment.getProviderRef());
            default -> throw new IllegalArgumentException("Unknown provider: " + payment.getProvider());
        };

        PaymentStatus newStatus = mapStatus(rawStatus);
        payment.setStatus(newStatus);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    private PaymentStatus mapStatus(String raw) {
        if (raw == null) return PaymentStatus.PENDING;
        String s = raw.toUpperCase();
        if (s.contains("SUCC") || s.contains("SUCCESS") || s.contains("PAID")) return PaymentStatus.SUCCEEDED;
        if (s.contains("REFUND")) return PaymentStatus.REFUNDED;
        if (s.contains("FAIL") || s.contains("CANCEL")) return PaymentStatus.FAILED;
        return PaymentStatus.PENDING;
    }
}
