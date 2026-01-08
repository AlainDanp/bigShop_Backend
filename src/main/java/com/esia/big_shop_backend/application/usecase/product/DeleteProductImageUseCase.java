package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.repository.ProductImageRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductImageId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProductImageUseCase {
    private final ProductImageRepository productImageRepository;

    @Transactional
    public void execute(Long imageId) {
        ProductImageId id = ProductImageId.of(imageId);
        if (!productImageRepository.existsById(id)) {
            throw new IllegalArgumentException("Product image not found with id: " + imageId);
        }
        productImageRepository.deleteById(id);
    }
}
