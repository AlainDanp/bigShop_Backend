package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.query.GetOrderByNumberQuery;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetOrderByNumberUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Order execute(GetOrderByNumberQuery query) {
        return orderRepository.findByOrderNumber(query.getOrderNumber())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with number: " + query.getOrderNumber()));
    }
}
