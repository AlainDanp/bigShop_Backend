package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.command.UpdateOrderStatusCommand;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.service.OrderDomainService;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;

    @Transactional
    public Order execute(UpdateOrderStatusCommand command) {
        Order order = orderRepository.findById(OrderId.of(command.getOrderId()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + command.getOrderId()));

        OrderStatus newStatus = command.getNewStatus();

        // Apply status transition based on the new status using domain service
        switch (newStatus) {
            case CONFIRMED -> orderDomainService.confirm(order);
            case PROCESSING -> orderDomainService.startProcessing(order);
            case SHIPPED -> orderDomainService.ship(order);
            case DELIVERED -> orderDomainService.deliver(order);
            case CANCELLED -> orderDomainService.cancel(order);
            case PENDING -> throw new IllegalStateException("Cannot revert order to PENDING status");
        }

        return orderRepository.save(order);
    }
}
