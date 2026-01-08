package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {
    private final OrderRepository orderRepository;

    @Transactional
    public Order execute(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        // Apply status transition based on the new status
        switch (newStatus) {
            case CONFIRMED -> order.confirm();
            case PROCESSING -> order.startProcessing();
            case SHIPPED -> order.ship();
            case DELIVERED -> order.deliver();
            case CANCELLED -> order.cancel();
            case PENDING -> throw new IllegalStateException("Cannot revert order to PENDING status");
        }

        return orderRepository.save(order);
    }
}
