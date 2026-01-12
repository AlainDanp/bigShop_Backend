package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.application.usecase.product.command.UpdateProductStockCommand;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.service.ProductDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductStockUseCase {
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;

    @Transactional
    public Product execute(UpdateProductStockCommand command) {
        Product product = productRepository.findById(ProductId.of(command.getProductId()))
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + command.getProductId()));

        switch (command.getOperation()) {
            case INCREASE -> productDomainService.increaseStock(product, command.getQuantity());
            case DECREASE -> productDomainService.decreaseStock(product, command.getQuantity());
        }

        return productRepository.save(product);
    }
}
