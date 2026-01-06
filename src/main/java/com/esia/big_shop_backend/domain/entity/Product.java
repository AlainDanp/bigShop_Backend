package com.esia.big_shop_backend.domain.entity;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private final Product id;
    private String name;
    private String description;
    private int price;
    private int discountPrice;
    private boolean active;
    private Category categoryId;
    private int quantity;

    public void applyDiscount(int discountPrice) {
        if (discountPrice > this.price) {
            throw new IllegalArgumentException("Discount price cannot exceed original price");
        }
        this.discountPrice = discountPrice;
    }
    public void inscreasePrice(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount must be positive");
        }
        this.price += amount;
    }
    public void decreaseStock(int quantity) {
        if (quantity > quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        this.quantity -= quantity;
    }

    public boolean isInStock() {
        return quantity > 0;
    }

}
