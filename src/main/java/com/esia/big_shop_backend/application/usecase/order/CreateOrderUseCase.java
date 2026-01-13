package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.usecase.order.command.CreateOrderCommand;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.event.OrderCreatedEvent;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.service.OrderDomainService;
import com.esia.big_shop_backend.domain.service.ProductDomainService;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.PhoneNumber;
import com.esia.big_shop_backend.domain.valueobject.ShippingInfo;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;
    private final OrderDomainService orderDomainService;
    private final EventPublisher eventPublisher;

    @Transactional
    public Order execute(CreateOrderCommand command) {
        ShippingInfo shippingInfo = new ShippingInfo(
                command.getShippingFullName(),
                PhoneNumber.of(command.getShippingPhone()),
                command.getShippingAddress()
        );

        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Create initial empty order
        Order order = new Order(
                null,
                orderNumber,
                UserId.of(command.getUserId()),
                new ArrayList<>(),
                OrderStatus.PENDING,
                0,
                0,
                shippingInfo,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        for (CreateOrderCommand.OrderItemDto itemDto : command.getItems()) {
            Product product = productRepository.findById(ProductId.of(itemDto.getProductId()))
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + itemDto.getProductId()));

            if (!productDomainService.isInStock(product)) {
                throw new IllegalStateException("Product is out of stock: " + product.getName());
            }

            if (product.getStockQuantity() < itemDto.getQuantity()) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }

            productDomainService.decreaseStock(product, itemDto.getQuantity());
            productRepository.save(product);

            // Calculate item total for the OrderItem record
            double itemTotalDouble = product.getPrice().multiply(itemDto.getQuantity()).getAmount();
            int itemTotalInt = (int) Math.round(itemTotalDouble);

            OrderItem orderItem = new OrderItem(
                    null,
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    itemDto.getQuantity(),
                    itemTotalInt,
                    itemTotalDouble
            );

            // Use domain service to add item and recalculate order totals
            orderDomainService.addItem(order, orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        // Publish event
        eventPublisher.publish(OrderCreatedEvent.of(
                savedOrder.getId(),
                savedOrder.getUserId(),
                new Money(savedOrder.getTotalAmount(), "XAF") // Assuming currency is XAF as per other files
        ));

        return savedOrder;
    }
}
