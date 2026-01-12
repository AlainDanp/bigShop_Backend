package com.esia.big_shop_backend.application.usecase.order.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderCommand {
    private final Long userId;
    private final List<OrderItemDto> items;
    private final String shippingFullName;
    private final String shippingPhone;
    private final String shippingAddress;

    @Getter
    @AllArgsConstructor
    public static class OrderItemDto {
        private final Long productId;
        private final int quantity;
    }
}
