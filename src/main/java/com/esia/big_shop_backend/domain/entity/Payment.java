package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;

    private Long orderId;
    private Long userId;

    private BigDecimal amount;
    private String currency;

    private String provider;
    private String providerRef;
    private PaymentStatus status;

    private Instant createdAt;
    private Instant updatedAt;
}
