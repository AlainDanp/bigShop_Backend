package com.esia.big_shop_backend.domain.event;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.PaymentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentProcessedEvent {
    private Long paymentId;
    private Long orderId;
    private Double amount;
    private String status;
    private String provider; // e.g., "STRIPE"
    private LocalDateTime occurredOn;

    public static PaymentProcessedEvent of(PaymentId paymentId, OrderId orderId, Money amount, String status, String provider) {
        return new PaymentProcessedEvent(
                paymentId.getValue(),
                orderId.getValue(),
                amount.getAmount(),
                status,
                provider,
                LocalDateTime.now()
        );
    }
}
