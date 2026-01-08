package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetAllOrdersUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<Order> execute(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
