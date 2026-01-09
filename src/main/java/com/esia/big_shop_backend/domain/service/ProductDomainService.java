package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import org.springframework.stereotype.Service;

@Service
public class ProductDomainService {

    public void applyDiscount(Product product, Money discountPrice) {
        if (discountPrice.isGreaterThan(product.getPrice())) {
            throw new IllegalArgumentException("Discount price cannot exceed original price");
        }
        product.setDiscountPrice(discountPrice);
    }

    public void removeDiscount(Product product) {
        product.setDiscountPrice(null);
    }

    public void increaseStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        product.setStockQuantity(product.getStockQuantity() + quantity);
    }

    public void decreaseStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (quantity > product.getStockQuantity()) {
            throw new IllegalArgumentException("Not enough stock available. Available: " + product.getStockQuantity() + ", requested: " + quantity);
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
    }

    public boolean isInStock(Product product) {
        return product.getStockQuantity() != null && product.getStockQuantity() > 0;
    }

    public boolean hasDiscount(Product product) {
        return product.getDiscountPrice() != null && product.getDiscountPrice().isLessThan(product.getPrice());
    }

    public Money getEffectivePrice(Product product) {
        if (hasDiscount(product)) {
            return product.getDiscountPrice();
        }
        return product.getPrice();
    }

    public void activate(Product product) {
        product.setActive(true);
    }

    public void deactivate(Product product) {
        product.setActive(false);
    }

    public void updateDetails(Product product, String name, String description, Money price, Money discountPrice) {
        if (name != null && !name.isBlank()) {
            product.setName(name);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (discountPrice != null) {
            if (discountPrice.isGreaterThan(price != null ? price : product.getPrice())) {
                throw new IllegalArgumentException("Discount price cannot exceed original price");
            }
            product.setDiscountPrice(discountPrice);
        }
    }

    public void updateCategory(Product product, CategoryId categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        product.setCategoryId(categoryId);
    }

    public void updatePrice(Product product, Money newPrice) {
        if (newPrice == null || newPrice.getAmount() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        product.setPrice(newPrice);


        if (product.getDiscountPrice() != null && product.getDiscountPrice().isGreaterThan(newPrice)) {
            product.setDiscountPrice(null);
        }
    }

    public boolean canBeSold(Product product) {
        return product.isActive() && isInStock(product);
    }
}
