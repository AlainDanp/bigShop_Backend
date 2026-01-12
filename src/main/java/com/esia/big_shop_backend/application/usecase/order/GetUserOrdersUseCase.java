package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.query.GetUserOrdersQuery;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserOrdersUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> execute(GetUserOrdersQuery query) {
        return orderRepository.findByUserId(UserId.of(query.getUserId()), query.getPage());
    }
}
