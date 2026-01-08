package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.Money;
import org.springframework.stereotype.Service;

/**
 * Domain Service for pricing calculations
 * Handles complex pricing logic involving multiple entities
 */
@Service
public class PricingService {

    /**
     * Calculate the final price of a product including discount if applicable
     */
    public Money calculateFinalPrice(Product product) {
        if (product.hasDiscount()) {
            return product.getDiscountPrice();
        }
        return product.getPrice();
    }

    /**
     * Calculate discount amount
     */
    public Money calculateDiscountAmount(Product product) {
        if (product.hasDiscount()) {
            return product.getPrice().subtract(product.getDiscountPrice());
        }
        return Money.ZERO;
    }

    /**
     * Calculate discount percentage
     */
    public double calculateDiscountPercentage(Product product) {
        if (!product.hasDiscount()) {
            return 0.0;
        }

        double originalPrice = product.getPrice().getAmount();
        double discountPrice = product.getDiscountPrice().getAmount();

        return ((originalPrice - discountPrice) / originalPrice) * 100;
    }

    /**
     * Calculate subtotal for order (without taxes and shipping)
     */
    public Money calculateOrderSubtotal(Order order) {
        return order.getTotalAmount();
    }

    /**
     * Calculate tax amount (example: 18% VAT for Cameroon)
     */
    public Money calculateTaxAmount(Money subtotal, double taxRate) {
        double taxAmount = subtotal.getAmount() * taxRate;
        return new Money(taxAmount, subtotal.getCurrency());
    }

    /**
     * Calculate shipping cost based on order total
     */
    public Money calculateShippingCost(Money orderSubtotal) {
        // Example shipping logic:
        // - Free shipping for orders over 50,000 XAF
        // - Fixed 2,500 XAF for orders between 10,000 and 50,000 XAF
        // - Fixed 5,000 XAF for orders below 10,000 XAF

        double amount = orderSubtotal.getAmount();
        String currency = orderSubtotal.getCurrency();

        if (amount >= 50000) {
            return Money.ZERO;
        } else if (amount >= 10000) {
            return new Money(2500, currency);
        } else {
            return new Money(5000, currency);
        }
    }

    /**
     * Calculate order grand total (subtotal + tax + shipping)
     */
    public Money calculateGrandTotal(Money subtotal, Money tax, Money shipping) {
        return subtotal.add(tax).add(shipping);
    }

    /**
     * Check if customer is eligible for free shipping
     */
    public boolean isEligibleForFreeShipping(Money orderSubtotal) {
        return orderSubtotal.getAmount() >= 50000;
    }

    /**
     * Calculate amount needed for free shipping
     */
    public Money calculateAmountForFreeShipping(Money orderSubtotal) {
        double current = orderSubtotal.getAmount();
        double threshold = 50000;

        if (current >= threshold) {
            return Money.ZERO;
        }

        return new Money(threshold - current, orderSubtotal.getCurrency());
    }
}
