package com.esia.big_shop_backend.application.usecase.cart.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCartTotalQuery {
    private final Long userId;
}
