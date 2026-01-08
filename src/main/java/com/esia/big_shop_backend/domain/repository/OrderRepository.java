package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    Optional<Order> findByOrderNumber(String orderNumber);
    Page<Order> findAll(Pageable pageable);
    Page<Order> findByUserId(UserId userId, Pageable pageable);
    void deleteById(OrderId id);
    boolean existsById(OrderId id);
}
