package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.event.OrderShippedEvent;
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
    private final EventPublisher eventPublisher;

    @Transactional
    public Order execute(OrderId orderId) {
        Order order = orderRepository.findById(OrderId.of(orderId.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        orderDomainService.ship(order);

        Order savedOrder = orderRepository.save(order);
        
        eventPublisher.publish(OrderShippedEvent.of(savedOrder.getId()));
        
        return savedOrder;
    }
}
