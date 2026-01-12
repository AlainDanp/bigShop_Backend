package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.query.GetAllOrdersQuery;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllOrdersUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> execute(GetAllOrdersQuery query) {
        return orderRepository.findAll(0, Integer.MAX_VALUE);
    }
}
