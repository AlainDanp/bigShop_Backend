package com.esia.big_shop_backend.application.usecase.order.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllOrdersQuery {
    private int page = 0;
    private int size = 20;
}
