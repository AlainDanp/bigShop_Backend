// java
package com.esia.big_shop_backend.application.usecase.order;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;

    @Transactional
    public Order execute(CreateOrderCommand command) {
        List<OrderItem> orderItems = new ArrayList<>();
        int totalItems = 0;
        int totalAmount = 0;

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

            // Calcul du total pour cet item : multiply retourne un double -> on arrondit et convertit en int
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
            orderItems.add(orderItem);

            totalItems += itemDto.getQuantity();
            totalAmount += itemTotalInt;
        }

        ShippingInfo shippingInfo = new ShippingInfo(
                command.getShippingFullName(),
                PhoneNumber.of(command.getShippingPhone()),
                command.getShippingAddress()
        );

        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Passer les totaux avant le ShippingInfo conformément à la signature du constructeur Order
        Order order = new Order(
                null,
                orderNumber,
                UserId.of(command.getUserId()),
                orderItems,
                OrderStatus.PENDING,
                totalItems,
                totalAmount,
                shippingInfo,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return orderRepository.save(order);
    }
}
