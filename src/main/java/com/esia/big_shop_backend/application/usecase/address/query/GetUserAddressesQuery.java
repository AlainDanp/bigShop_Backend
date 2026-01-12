package com.esia.big_shop_backend.application.usecase.address.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserAddressesQuery {
    private final Long userId;
}
