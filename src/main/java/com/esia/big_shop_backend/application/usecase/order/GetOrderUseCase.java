package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.query.GetOrderQuery;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetOrderUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Order execute(GetOrderQuery query) {
        return orderRepository.findById(OrderId.of(query.getOrderId()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + query.getOrderId()));
    }
}
