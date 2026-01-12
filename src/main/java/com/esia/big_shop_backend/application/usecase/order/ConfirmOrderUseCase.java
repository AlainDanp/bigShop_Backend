package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.command.ConfirmOrderCommand;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.service.OrderDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfirmOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;

    @Transactional
    public Order execute(ConfirmOrderCommand command) {
        Order order = orderRepository.findById(OrderId.of(command.getOrderId()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + command.getOrderId()));

        orderDomainService.confirm(order);

        return orderRepository.save(order);
    }
}
