package com.esia.big_shop_backend.application.usecase.admin.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopProductStatistic {
    private final Long productId;
    private final String productName;
    private final int totalSold;
    private final double revenue;
}
