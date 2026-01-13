package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderItemId;

import java.util.Optional;

public interface OrderItemRepository {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(OrderItemId id);
}
