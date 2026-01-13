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
public class OrderDeliveredEvent {
    private Long orderId;
    private LocalDateTime occurredOn;

    public static OrderDeliveredEvent of(OrderId orderId) {
        return new OrderDeliveredEvent(orderId.getValue(), LocalDateTime.now());
    }
}
