package com.esia.big_shop_backend.application.usecase.order.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserOrdersQuery {
    private Long userId;
    private int page = 0;
    private int size = 20;
}
