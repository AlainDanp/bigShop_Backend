package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.command.ShipOrderCommand;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.service.OrderDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;

    @Transactional
    public Order execute(ShipOrderCommand command) {
        Order order = orderRepository.findById(OrderId.of(command.getOrderId().getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + command.getOrderId()));

        orderDomainService.ship(order);

        return orderRepository.save(order);
    }
}
