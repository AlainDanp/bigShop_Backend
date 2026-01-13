package com.esia.big_shop_backend.domain.event;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private LocalDateTime occurredOn;

    public static OrderCreatedEvent of(OrderId orderId, UserId userId, Money totalAmount) {
        return new OrderCreatedEvent(
                orderId.getValue(),
                userId.getValue(),
                totalAmount.getAmount(),
                LocalDateTime.now()
        );
    }
}
