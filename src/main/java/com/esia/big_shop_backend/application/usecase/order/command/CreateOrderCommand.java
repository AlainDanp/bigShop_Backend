package com.esia.big_shop_backend.application.usecase.order.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderCommand {
    private final Long userId;
}
