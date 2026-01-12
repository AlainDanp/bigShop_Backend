package com.esia.big_shop_backend.application.usecase.payment;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.port.output.MtnPaymentPort;
import com.esia.big_shop_backend.application.port.output.OrangeMoneyPaymentPort;
import com.esia.big_shop_backend.application.port.output.StripePaymentPort;
import com.esia.big_shop_backend.application.usecase.payment.command.RefundPaymentCommand;
import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentStatus;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class RefundPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final StripePaymentPort stripePaymentPort;
    private final MtnPaymentPort mtnPaymentPort;
    private final OrangeMoneyPaymentPort orangeMoneyPaymentPort;
    private final CurrentUserPort currentUserPort;

    public Payment execute(RefundPaymentCommand cmd) {
        Long userId = currentUserPort.getUserIdByEmail(cmd.getUserEmail());

        Payment payment = paymentRepository.findById(cmd.getPaymentId())
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        if (!userId.equals(payment.getUserId())) {
            throw new IllegalArgumentException("Forbidden");
        }

        switch (payment.getProvider()) {
            case "STRIPE" -> stripePaymentPort.refund(payment.getProviderRef());
            case "MTN" -> mtnPaymentPort.refund(payment.getProviderRef());
            case "ORANGE_MONEY" -> orangeMoneyPaymentPort.refund(payment.getProviderRef());
            default -> throw new IllegalArgumentException("Unknown provider");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }
}
