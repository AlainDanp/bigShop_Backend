package com.esia.big_shop_backend.application.usecase.cart.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCartItemCommand {
    private final Long userId;
    private final Long productId;
    private final int quantity;
}
