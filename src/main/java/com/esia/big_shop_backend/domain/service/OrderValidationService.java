package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain Service for order validation logic
 */
@Service
public class OrderValidationService {

    private static final double MIN_ORDER_AMOUNT = 500.0; // Minimum 500 XAF
    private static final double MAX_ORDER_AMOUNT = 10000000.0; // Maximum 10M XAF
    private static final int MAX_ITEMS_PER_ORDER = 100;

    /**
     * Validate order before creation
     */
    public ValidationResult validateOrder(Order order, List<Product> products) {
        List<String> errors = new ArrayList<>();

        // Validate order has items
        if (order.getItems() == null || order.getItems().isEmpty()) {
            errors.add("Order must contain at least one item");
        }

        // Validate order amount
        Money total = order.getTotalAmount();
        if (total.getAmount() < MIN_ORDER_AMOUNT) {
            errors.add("Order amount must be at least " + MIN_ORDER_AMOUNT + " XAF");
        }

        if (total.getAmount() > MAX_ORDER_AMOUNT) {
            errors.add("Order amount cannot exceed " + MAX_ORDER_AMOUNT + " XAF");
        }

        // Validate number of items
        if (order.getItems().size() > MAX_ITEMS_PER_ORDER) {
            errors.add("Order cannot contain more than " + MAX_ITEMS_PER_ORDER + " items");
        }

        // Validate each item
        for (OrderItem item : order.getItems()) {
            if (item.getQuantity() <= 0) {
                errors.add("Item quantity must be positive");
            }

            if (item.getQuantity() > 999) {
                errors.add("Maximum quantity per item is 999");
            }
        }

        // Validate shipping info
        if (order.getShippingInfo() == null) {
            errors.add("Shipping information is required");
        } else {
            if (order.getShippingInfo().getFullName() == null ||
                order.getShippingInfo().getFullName().isBlank()) {
                errors.add("Shipping full name is required");
            }

            if (order.getShippingInfo().getPhoneNumber() == null) {
                errors.add("Shipping phone number is required");
            }

            if (order.getShippingInfo().getAddressText() == null ||
                order.getShippingInfo().getAddressText().isBlank()) {
                errors.add("Shipping address is required");
            }
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    /**
     * Validate if user can place order
     */
    public ValidationResult validateUserCanPlaceOrder(User user) {
        List<String> errors = new ArrayList<>();

        if (!user.isActive()) {
            errors.add("User account is not active");
        }

        if (!user.isEmailVerified()) {
            errors.add("Email must be verified before placing orders");
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    /**
     * Validate product availability for order
     */
    public ValidationResult validateProductAvailability(Product product, int requestedQuantity) {
        List<String> errors = new ArrayList<>();

        if (!product.isActive()) {
            errors.add("Product is not available: " + product.getName());
        }

        if (!product.isInStock()) {
            errors.add("Product is out of stock: " + product.getName());
        }

        if (product.getStockQuantity() < requestedQuantity) {
            errors.add("Insufficient stock for product: " + product.getName() +
                      ". Available: " + product.getStockQuantity() +
                      ", Requested: " + requestedQuantity);
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    /**
     * Validate order can be cancelled
     */
    public ValidationResult validateOrderCancellation(Order order) {
        List<String> errors = new ArrayList<>();

        if (!order.canBeCancelled()) {
            errors.add("Order cannot be cancelled in current status: " + order.getStatus());
        }

        if (order.getStatus() == OrderStatus.DELIVERED) {
            errors.add("Delivered orders cannot be cancelled");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            errors.add("Order is already cancelled");
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    /**
     * Validate order status transition
     */
    public ValidationResult validateStatusTransition(Order order, OrderStatus newStatus) {
        List<String> errors = new ArrayList<>();
        OrderStatus currentStatus = order.getStatus();

        // Define valid transitions
        boolean isValidTransition = switch (currentStatus) {
            case PENDING -> newStatus == OrderStatus.CONFIRMED || newStatus == OrderStatus.CANCELLED;
            case CONFIRMED -> newStatus == OrderStatus.PROCESSING || newStatus == OrderStatus.CANCELLED;
            case PROCESSING -> newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.CANCELLED;
            case SHIPPED -> newStatus == OrderStatus.DELIVERED;
            case DELIVERED, CANCELLED -> false; // Terminal states
        };

        if (!isValidTransition) {
            errors.add("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    /**
     * Validate order total matches items
     */
    public ValidationResult validateOrderTotal(Order order) {
        List<String> errors = new ArrayList<>();

        Money calculatedTotal = order.getItems().stream()
                .map(OrderItem::getSubtotal)
                .reduce(Money.ZERO, Money::add);

        Money orderTotal = order.getTotalAmount();

        if (!calculatedTotal.equals(orderTotal)) {
            errors.add("Order total mismatch. Expected: " + calculatedTotal.getAmount() +
                      ", Actual: " + orderTotal.getAmount());
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    /**
     * Validation result wrapper
     */
    public static class ValidationResult {
        private final boolean valid;
        private final List<String> errors;

        public ValidationResult(boolean valid, List<String> errors) {
            this.valid = valid;
            this.errors = errors;
        }

        public boolean isValid() {
            return valid;
        }

        public List<String> getErrors() {
            return errors;
        }

        public String getErrorMessage() {
            return String.join(", ", errors);
        }
    }
}
