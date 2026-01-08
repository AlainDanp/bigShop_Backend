package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private final ProductId id;
    private String name;
    private String description;
    private Money price;
    private Money discountPrice;
    private Integer stockQuantity;
    private CategoryId categoryId;
    private boolean active;

    // Logique métier
    public void applyDiscount(Money discountPrice) {
        if (discountPrice.isGreaterThan(price)) {
            throw new IllegalArgumentException("Discount price cannot exceed original price");
        }
        this.discountPrice = discountPrice;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity) {
        if (quantity > stockQuantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        this.stockQuantity -= quantity;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean hasDiscount() {
        return discountPrice != null && discountPrice.isLessThan(price);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void updateDetails(String name, String description, Money price, Money discountPrice) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
        if (price != null) {
            this.price = price;
        }
        if (discountPrice != null && !discountPrice.isGreaterThan(price)) {
            this.discountPrice = discountPrice;
        }
    }

    public void updateCategory(CategoryId categoryId) {
        if (categoryId != null) {
            this.categoryId = categoryId;
        }
    }
}
