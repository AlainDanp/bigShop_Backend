package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.CartItemId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    private final CartItemId id;
    private Product product;
    private int quantity;
    private Money price;

    public Money getSubTotal() {
        return price.multiply(quantity);
    }
}
