package com.esia.big_shop_backend.application.usecase.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeactivateProductCommand {
    private final Long productId;
}
