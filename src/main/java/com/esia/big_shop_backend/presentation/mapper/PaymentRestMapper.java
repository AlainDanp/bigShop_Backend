package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.presentation.dto.request.payment.MtnPaymentRequest;
import com.esia.big_shop_backend.presentation.dto.request.payment.OrangeMoneyPaymentRequest;
import com.esia.big_shop_backend.presentation.dto.request.payment.StripePaymentRequest;
import com.esia.big_shop_backend.presentation.dto.response.payment.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentRestMapper {

    public PaymentResponse toResponse(Payment p, String clientSecret) {
        return PaymentResponse.builder()
                .paymentId(p.getId())
                .orderId(p.getOrderId())
                .provider(p.getProvider())
                .providerRef(p.getProviderRef())
                .status(p.getStatus().name())
                .clientSecret(clientSecret)
                .build();
    }

    public PaymentResponse toResponse(Payment p) {
        return toResponse(p, null);
    }

    // Note: Les méthodes pour mapper les requests vers les commands peuvent être ajoutées
    // selon les besoins spécifiques de vos use cases de paiement.
    // Par exemple:
    // public ProcessStripePaymentCommand toStripeCommand(StripePaymentRequest request) { ... }
    // public ProcessMtnPaymentCommand toMtnCommand(MtnPaymentRequest request) { ... }
    // public ProcessOrangeMoneyCommand toOrangeCommand(OrangeMoneyPaymentRequest request) { ... }
}
