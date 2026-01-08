package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Domain Service for stock and inventory management
 */
@Service
public class StockManagementService {

    private static final int LOW_STOCK_THRESHOLD = 10;
    private static final int CRITICAL_STOCK_THRESHOLD = 5;
    private static final int OUT_OF_STOCK_THRESHOLD = 0;

    /**
     * Check if product has sufficient stock for requested quantity
     */
    public boolean hasSufficientStock(Product product, int requestedQuantity) {
        return product.getStockQuantity() >= requestedQuantity;
    }

    /**
     * Check if product is in low stock
     */
    public boolean isLowStock(Product product) {
        return product.getStockQuantity() > CRITICAL_STOCK_THRESHOLD
                && product.getStockQuantity() <= LOW_STOCK_THRESHOLD;
    }

    /**
     * Check if product is in critical stock level
     */
    public boolean isCriticalStock(Product product) {
        return product.getStockQuantity() > OUT_OF_STOCK_THRESHOLD
                && product.getStockQuantity() <= CRITICAL_STOCK_THRESHOLD;
    }

    /**
     * Check if product is out of stock
     */
    public boolean isOutOfStock(Product product) {
        return product.getStockQuantity() <= OUT_OF_STOCK_THRESHOLD;
    }

    /**
     * Get stock status as enum
     */
    public StockStatus getStockStatus(Product product) {
        if (isOutOfStock(product)) {
            return StockStatus.OUT_OF_STOCK;
        } else if (isCriticalStock(product)) {
            return StockStatus.CRITICAL;
        } else if (isLowStock(product)) {
            return StockStatus.LOW;
        } else {
            return StockStatus.IN_STOCK;
        }
    }

    /**
     * Calculate reorder quantity based on current stock and demand
     */
    public int calculateReorderQuantity(Product product, int averageMonthlySales) {
        int currentStock = product.getStockQuantity();
        int safetyStock = averageMonthlySales / 2; // 2 weeks buffer
        int reorderPoint = averageMonthlySales + safetyStock;

        if (currentStock < reorderPoint) {
            return reorderPoint - currentStock;
        }

        return 0;
    }

    /**
     * Check if reorder is needed
     */
    public boolean needsReorder(Product product, int reorderPoint) {
        return product.getStockQuantity() <= reorderPoint;
    }

    /**
     * Calculate stock value for a product
     */
    public double calculateStockValue(Product product) {
        return product.getPrice().getAmount() * product.getStockQuantity();
    }

    /**
     * Calculate total stock value for multiple products
     */
    public double calculateTotalStockValue(List<Product> products) {
        return products.stream()
                .mapToDouble(this::calculateStockValue)
                .sum();
    }

    /**
     * Get products that need immediate restocking
     */
    public List<Product> getProductsNeedingRestock(List<Product> products) {
        return products.stream()
                .filter(product -> isCriticalStock(product) || isOutOfStock(product))
                .filter(Product::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Get stock level distribution
     */
    public Map<StockStatus, Long> getStockLevelDistribution(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        this::getStockStatus,
                        Collectors.counting()
                ));
    }

    /**
     * Validate stock reservation
     */
    public boolean canReserveStock(Product product, int quantity) {
        return product.isActive()
                && product.isInStock()
                && hasSufficientStock(product, quantity);
    }

    /**
     * Calculate days of inventory remaining based on average daily sales
     */
    public int calculateDaysOfInventory(Product product, double averageDailySales) {
        if (averageDailySales <= 0) {
            return Integer.MAX_VALUE; // Infinite days if no sales
        }

        return (int) Math.floor(product.getStockQuantity() / averageDailySales);
    }

    /**
     * Check if product will be out of stock within specified days
     */
    public boolean willBeOutOfStockSoon(Product product, double averageDailySales, int daysThreshold) {
        int daysOfInventory = calculateDaysOfInventory(product, averageDailySales);
        return daysOfInventory <= daysThreshold;
    }

    /**
     * Stock status enum
     */
    public enum StockStatus {
        IN_STOCK,
        LOW,
        CRITICAL,
        OUT_OF_STOCK
    }
}
