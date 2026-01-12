package com.esia.big_shop_backend.application.usecase.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProductStockCommand {
    private final Long productId;
    private final int quantity;
    private final StockOperation operation;

    public enum StockOperation {
        INCREASE, DECREASE
    }
}
