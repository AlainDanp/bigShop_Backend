package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentStatus;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.PaymentJpaEntity;


public class PaymentMapper {

    public Payment toDomain(PaymentJpaEntity e) {
        if (e == null) return null;
        return Payment.builder()
                .id(e.getId())
                .orderId(e.getOrderId())
                .userId(e.getUserId())
                .amount(e.getAmount())
                .currency(e.getCurrency())
                .provider(e.getProvider())
                .providerRef(e.getProviderRef())
                .status(PaymentStatus.valueOf(e.getStatus()))
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public PaymentJpaEntity toEntity(Payment p) {
        if (p == null) return null;
        PaymentJpaEntity e = new PaymentJpaEntity();
        e.setId(p.getId());
        e.setOrderId(p.getOrderId());
        e.setUserId(p.getUserId());
        e.setAmount(p.getAmount());
        e.setCurrency(p.getCurrency());
        e.setProvider(p.getProvider());
        e.setProviderRef(p.getProviderRef());
        e.setStatus(p.getStatus().name());
        e.setCreatedAt(p.getCreatedAt());
        e.setUpdatedAt(p.getUpdatedAt());
        return e;
    }
}
