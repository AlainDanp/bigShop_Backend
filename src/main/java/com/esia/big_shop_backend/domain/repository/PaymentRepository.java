package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    Optional<Payment> findByOrderId(Long orderId);

    Optional<Payment> findByProviderRef(String providerRef);
}
