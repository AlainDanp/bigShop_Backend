package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;


import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.PaymentMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentMapper mapper = new PaymentMapper();

    @Override
    public Payment save(Payment payment) {
        var saved = jpaRepository.save(mapper.toEntity(payment));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        return jpaRepository.findFirstByOrderId(orderId).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByProviderRef(String providerRef) {
        return jpaRepository.findFirstByProviderRef(providerRef).map(mapper::toDomain);
    }
}
