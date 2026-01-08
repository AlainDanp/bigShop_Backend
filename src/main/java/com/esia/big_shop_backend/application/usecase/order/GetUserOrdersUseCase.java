package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetUserOrdersUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<Order> execute(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(UserId.of(userId), pageable);
    }
}
