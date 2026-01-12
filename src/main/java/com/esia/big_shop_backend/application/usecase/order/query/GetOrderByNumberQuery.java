package com.esia.big_shop_backend.application.usecase.order.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrderByNumberQuery {
    private final String orderNumber;
}
