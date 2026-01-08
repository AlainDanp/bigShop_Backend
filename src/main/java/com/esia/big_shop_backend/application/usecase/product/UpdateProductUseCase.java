package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product execute(UpdateProductCommand command) {
        Product product = productRepository.findById(ProductId.of(command.getProductId()))
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + command.getProductId()));

        // Verify category exists if provided
        if (command.getCategoryId() != null) {
            CategoryId categoryId = CategoryId.of(command.getCategoryId());
            if (!categoryRepository.existsById(categoryId)) {
                throw new IllegalArgumentException("Category not found with id: " + command.getCategoryId());
            }
            product.updateCategory(categoryId);
        }

        // Update product details
        Money price = command.getPrice() != null ? new Money(command.getPrice(), "XAF") : null;
        Money discountPrice = command.getDiscountPrice() != null ? new Money(command.getDiscountPrice(), "XAF") : null;

        product.updateDetails(command.getName(), command.getDescription(), price, discountPrice);

        // Update stock if provided
        if (command.getStockQuantity() != null) {
            int currentStock = product.getStockQuantity();
            int difference = command.getStockQuantity() - currentStock;
            if (difference > 0) {
                product.increaseStock(difference);
            } else if (difference < 0) {
                product.decreaseStock(Math.abs(difference));
            }
        }

        return productRepository.save(product);
    }
}
