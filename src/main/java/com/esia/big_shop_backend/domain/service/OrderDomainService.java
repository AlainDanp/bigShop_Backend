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
        order.setUpdatedAt(LocalDateTime.now());
    }

    public void removeItem(Order order, OrderItem item) {
        if (order.getItems() != null) {
            order.getItems().remove(item);
            order.setUpdatedAt(LocalDateTime.now());
        }
    }

    public boolean canBeCancelled(Order order) {
        return order.getStatus() != OrderStatus.DELIVERED && order.getStatus() != OrderStatus.CANCELLED;
    }

    public boolean isInProgress(Order order) {
        return order.getStatus() == OrderStatus.CONFIRMED
                || order.getStatus() == OrderStatus.PROCESSING
                || order.getStatus() == OrderStatus.SHIPPED;
    }

    public boolean isPending(Order order) {
        return order.getStatus() == OrderStatus.PENDING;
    }

    public boolean isCompleted(Order order) {
        return order.getStatus() == OrderStatus.DELIVERED;
    }

    public boolean isCancelled(Order order) {
        return order.getStatus() == OrderStatus.CANCELLED;
    }

    public int getTotalItemsCount(Order order) {
        if (order.getItems() == null) {
            return 0;
        }
        return order.getItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }
}
