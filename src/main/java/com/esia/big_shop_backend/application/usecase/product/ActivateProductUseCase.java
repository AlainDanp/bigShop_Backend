package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.usecase.product.command.ActivateProductCommand;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.event.ProductUpdatedEvent;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.service.ProductDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;
    private final EventPublisher eventPublisher;

    @Transactional
    public Product execute(ActivateProductCommand command) {
        Product product = productRepository.findById(ProductId.of(command.getProductId()))
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + command.getProductId()));

        productDomainService.activate(product);
        Product savedProduct = productRepository.save(product);
        
        eventPublisher.publish(ProductUpdatedEvent.of(savedProduct.getId()));
        
        return savedProduct;
    }
}
