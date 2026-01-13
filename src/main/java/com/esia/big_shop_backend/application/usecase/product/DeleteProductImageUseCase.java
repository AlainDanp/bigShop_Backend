package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.domain.entity.ProductImage;
import com.esia.big_shop_backend.domain.event.ProductUpdatedEvent;
import com.esia.big_shop_backend.domain.repository.ProductImageRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductImageId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProductImageUseCase {
    private final ProductImageRepository productImageRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public void execute(Long imageId) {
        ProductImageId id = ProductImageId.of(imageId);
        
        ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product image not found with id: " + imageId));
        
        productImageRepository.deleteById(id);
        
        eventPublisher.publish(ProductUpdatedEvent.of(image.getProductId()));
    }
}
