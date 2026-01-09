package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageDomainService {

    public void markAsPrimary(ProductImage productImage) {
        productImage.setPrimary(true);
    }

    public void unmarkAsPrimary(ProductImage productImage) {
        productImage.setPrimary(false);
    }

    public void ensureSinglePrimaryImage(List<ProductImage> productImages, ProductImage newPrimary) {
        if (productImages == null || productImages.isEmpty()) {
            return;
        }

        for (ProductImage image : productImages) {
            if (!image.getId().equals(newPrimary.getId())) {
                image.setPrimary(false);
            }
        }

        newPrimary.setPrimary(true);
    }

    public ProductImage findPrimaryImage(List<ProductImage> productImages) {
        if (productImages == null || productImages.isEmpty()) {
            return null;
        }

        return productImages.stream()
                .filter(ProductImage::isPrimary)
                .findFirst()
                .orElse(null);
    }

    public boolean hasPrimaryImage(List<ProductImage> productImages) {
        return findPrimaryImage(productImages) != null;
    }
}
