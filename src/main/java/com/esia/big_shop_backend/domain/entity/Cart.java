package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.ids.CartId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private final CartId id;
    private final UserId userId;
    private List<CartItem> items;
}
