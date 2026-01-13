package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.event.OrderDeliveredEvent;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.service.OrderDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliverOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;
    private final EventPublisher eventPublisher;

    @Transactional
    public Order execute(OrderId orderId) {
        Order order = orderRepository.findById(OrderId.of(orderId.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        orderDomainService.deliver(order);

        Order savedOrder = orderRepository.save(order);
        
        eventPublisher.publish(OrderDeliveredEvent.of(savedOrder.getId()));
        
        return savedOrder;
    }
}
