package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.ShippingInfo;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private final OrderId id;
    private final String orderNumber;
    private final UserId userId;
    private List<OrderItem> items;
    private OrderStatus status;
    private final ShippingInfo shippingInfo;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
