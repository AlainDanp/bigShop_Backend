package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;

import java.util.List;
import java.util.Optional;
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findAll(int page, int size);
    List<Order> findByUserId(UserId userId,int page);
    void deleteById(OrderId id);
    boolean existsById(OrderId id);
}
