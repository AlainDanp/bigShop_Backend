package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.CartItem;
import com.esia.big_shop_backend.domain.valueobject.ids.CartItemId;

import java.util.Optional;

public interface CartItemRepository {
    CartItem save(CartItem cartItem);
    Optional<CartItem> findById(CartItemId id);
    void deleteById(CartItemId id);
}
