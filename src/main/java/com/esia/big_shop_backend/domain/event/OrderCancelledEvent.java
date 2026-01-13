package com.esia.big_shop_backend.domain.event;

import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCancelledEvent {
    private Long orderId;
    private LocalDateTime occurredOn;

    public static OrderCancelledEvent of(OrderId orderId) {
        return new OrderCancelledEvent(orderId.getValue(), LocalDateTime.now());
    }
}
