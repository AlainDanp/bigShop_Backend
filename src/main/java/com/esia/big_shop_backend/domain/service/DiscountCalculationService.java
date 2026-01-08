package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.Money;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain Service for discount and promotion calculations
 */
@Service
public class DiscountCalculationService {

    /**
     * Apply percentage discount to a product
     */
    public Money applyPercentageDiscount(Money originalPrice, double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }

        double discountAmount = originalPrice.getAmount() * (discountPercentage / 100);
        double finalPrice = originalPrice.getAmount() - discountAmount;

        return new Money(finalPrice, originalPrice.getCurrency());
    }

    /**
     * Apply fixed amount discount to a product
     */
    public Money applyFixedDiscount(Money originalPrice, Money discountAmount) {
        if (discountAmount.isGreaterThan(originalPrice)) {
            throw new IllegalArgumentException("Discount cannot exceed product price");
        }

        return originalPrice.subtract(discountAmount);
    }

    /**
     * Calculate bulk discount based on quantity
     * Example: 5% for 5-9 items, 10% for 10-19 items, 15% for 20+ items
     */
    public double calculateBulkDiscountPercentage(int quantity) {
        if (quantity >= 20) {
            return 15.0;
        } else if (quantity >= 10) {
            return 10.0;
        } else if (quantity >= 5) {
            return 5.0;
        }
        return 0.0;
    }

    /**
     * Apply bulk discount to a price based on quantity
     */
    public Money applyBulkDiscount(Money unitPrice, int quantity) {
        double discountPercentage = calculateBulkDiscountPercentage(quantity);
        return applyPercentageDiscount(unitPrice, discountPercentage);
    }

    /**
     * Calculate total savings across multiple products
     */
    public Money calculateTotalSavings(List<Product> products) {
        return products.stream()
                .filter(Product::hasDiscount)
                .map(product -> product.getPrice().subtract(product.getDiscountPrice()))
                .reduce(Money.ZERO, Money::add);
    }

    /**
     * Check if a product has an active flash sale discount
     * Flash sales are typically high discounts (> 30%) for limited time
     */
    public boolean isFlashSale(Product product) {
        if (!product.hasDiscount()) {
            return false;
        }

        double discountPercentage = ((product.getPrice().getAmount() - product.getDiscountPrice().getAmount())
                / product.getPrice().getAmount()) * 100;

        return discountPercentage >= 30;
    }

    /**
     * Calculate the best available discount for a product
     * Compares standard discount with potential bulk discount
     */
    public Money calculateBestPrice(Product product, int quantity) {
        Money standardPrice = product.hasDiscount() ? product.getDiscountPrice() : product.getPrice();
        Money bulkPrice = applyBulkDiscount(product.getPrice(), quantity);

        return bulkPrice.isLessThan(standardPrice) ? bulkPrice : standardPrice;
    }

    /**
     * Validate discount is within acceptable range
     */
    public boolean isValidDiscount(Money originalPrice, Money discountPrice) {
        if (discountPrice.isGreaterThan(originalPrice)) {
            return false;
        }

        if (discountPrice.getAmount() < 0) {
            return false;
        }

        // Discount cannot be more than 90% off
        double discountPercentage = ((originalPrice.getAmount() - discountPrice.getAmount())
                / originalPrice.getAmount()) * 100;

        return discountPercentage <= 90;
    }

    /**
     * Calculate seasonal discount (e.g., Black Friday, Christmas sales)
     */
    public Money applySeasonalDiscount(Money originalPrice, String season) {
        double discountPercentage = switch (season.toUpperCase()) {
            case "BLACK_FRIDAY" -> 40.0;
            case "CHRISTMAS" -> 30.0;
            case "NEW_YEAR" -> 25.0;
            case "SUMMER_SALE" -> 20.0;
            default -> 0.0;
        };

        return applyPercentageDiscount(originalPrice, discountPercentage);
    }
}
