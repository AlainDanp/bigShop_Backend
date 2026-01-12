package com.esia.big_shop_backend.application.usecase.order.command;

import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateOrderStatusCommand {
    private final Long orderId;
    private final OrderStatus newStatus;
}
