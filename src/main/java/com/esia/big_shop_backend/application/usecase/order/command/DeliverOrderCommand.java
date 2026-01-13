package com.esia.big_shop_backend.application.usecase.order.command;

import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliverOrderCommand {
    private final OrderId orderId;
}
