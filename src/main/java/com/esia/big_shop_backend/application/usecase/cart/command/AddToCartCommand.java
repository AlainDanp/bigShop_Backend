package com.esia.big_shop_backend.application.usecase.cart.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddToCartCommand {
    private final Long userId;
    private final Long productId;
    private final int quantity;
}
