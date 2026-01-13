package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.usecase.product.command.AddProductImageCommand;
import com.esia.big_shop_backend.domain.entity.ProductImage;
import com.esia.big_shop_backend.domain.event.ProductUpdatedEvent;
import com.esia.big_shop_backend.domain.repository.ProductImageRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddProductImageUseCase {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public ProductImage execute(AddProductImageCommand command) {
        // Verify product exists
        ProductId productId = ProductId.of(command.getProductId());
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product not found with id: " + command.getProductId());
        }

        ProductImage productImage = new ProductImage(
                null,
                productId,
                command.getImageUrl(),
                command.isPrimary()
        );

        ProductImage savedImage = productImageRepository.save(productImage);
        
        eventPublisher.publish(ProductUpdatedEvent.of(productId));
        
        return savedImage;
    }
}
