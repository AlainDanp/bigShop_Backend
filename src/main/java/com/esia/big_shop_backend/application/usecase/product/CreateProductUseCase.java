package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;


@Service
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product execute(CreateProductCommand command) {

        CategoryId categoryId = CategoryId.of(command.getCategoryId());
        if (!categoryRepository.existsById(categoryId)) {
            throw new ErrorResponseException("Category not found with id: " + categoryId.getValue());
        }

        Product product = new Product(
                null,
                command.getName(),
                command.getDescription(),
                new Money(command.getPrice(), "XAF"),
                command.getDiscountPrice() != null ? new Money(command.getDiscountPrice(), "XAF") : null,
                command.getStockQuantity(),
                categoryId,
                true
        );

        // 3. Valider et sauvegarder
        return productRepository.save(product);
    }

}
