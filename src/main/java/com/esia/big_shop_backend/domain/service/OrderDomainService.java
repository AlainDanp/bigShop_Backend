package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OrderDomainService {

    public Money calculateTotalAmount(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return Money.ZERO;
        }
        return order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(item.getQuantity()))
                .reduce(Money.ZERO, Money::add);
    }

    public void confirm(Order order) {
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be confirmed. Current status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.CONFIRMED);
        order.setUpdatedAt(LocalDateTime.now());
    }

    public void startProcessing(Order order) {
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed orders can be processed. Current status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.PROCESSING);
        order.setUpdatedAt(LocalDateTime.now());
    }

    public void ship(Order order) {
        if (order.getStatus() != OrderStatus.PROCESSING) {
            throw new IllegalStateException("Only processing orders can be shipped. Current status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.SHIPPED);
        order.setUpdatedAt(LocalDateTime.now());
    }

    public void deliver(Order order) {
        if (order.getStatus() != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Only shipped orders can be delivered. Current status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.DELIVERED);
        order.setUpdatedAt(LocalDateTime.now());
    }

    public void cancel(Order order) {
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Delivered orders cannot be cancelled");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
    }

    public void addItem(Order order, OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }
        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }
        order.getItems().add(item);
        recalculateTotals(order);
        order.setUpdatedAt(LocalDateTime.now());
    }

    private void recalculateTotals(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            order.setTotalItems(0);
            order.setTotalAmount(0);
            return;
        }

        int totalItems = order.getItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        order.setTotalItems(totalItems);

        // Calculate total amount using Money logic then convert to int as per Entity definition
        Money totalMoney = order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(item.getQuantity()))
                .reduce(Money.ZERO, Money::add);
        
        order.setTotalAmount((int) Math.round(totalMoney.getAmount()));
    }


}
