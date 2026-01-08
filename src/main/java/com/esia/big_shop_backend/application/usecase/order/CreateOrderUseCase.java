package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order execute(CreateOrderCommand command) {
        // Create order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderCommand.OrderItemDto itemDto : command.getItems()) {
            Product product = productRepository.findById(ProductId.of(itemDto.getProductId()))
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + itemDto.getProductId()));

            if (!product.isInStock()) {
                throw new IllegalStateException("Product is out of stock: " + product.getName());
            }

            if (product.getStockQuantity() < itemDto.getQuantity()) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }

            // Decrease stock
            product.decreaseStock(itemDto.getQuantity());
            productRepository.save(product);

            // Create order item with snapshot of product info
            OrderItem orderItem = new OrderItem(
                    null,
                    product.getId(),
                    product.getName(),
                    product.hasDiscount() ? product.getDiscountPrice() : product.getPrice(),
                    itemDto.getQuantity()
            );
            orderItems.add(orderItem);
        }

        // Create shipping info
        ShippingInfo shippingInfo = new ShippingInfo(
                command.getShippingFullName(),
                PhoneNumber.of(command.getShippingPhone()),
                command.getShippingAddress()
        );

        // Generate order number
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Create order
        Order order = new Order(
                null,
                orderNumber,
                UserId.of(command.getUserId()),
                orderItems,
                OrderStatus.PENDING,
                shippingInfo,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return orderRepository.save(order);
    }
}
