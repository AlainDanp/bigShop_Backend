package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.usecase.order.command.DeleteOrderCommand;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteOrderUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void execute(DeleteOrderCommand command) {
        OrderId orderId = OrderId.of(command.getOrderId());
        
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order not found with id: " + command.getOrderId());
        }

        orderRepository.deleteById(orderId);
    }
}
