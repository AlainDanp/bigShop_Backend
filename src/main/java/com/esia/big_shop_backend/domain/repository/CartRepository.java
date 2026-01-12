package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.valueobject.ids.CartId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findById(CartId cartId);
    Optional<Cart> findByUserId(UserId userId);
    List<Cart> findAll(int page, int size);
    void deleteById(CartId cartId);
    void deleteByUserId(UserId userId);
    boolean existsById(CartId cartId);
    boolean existsByUserId(UserId userId);
}
