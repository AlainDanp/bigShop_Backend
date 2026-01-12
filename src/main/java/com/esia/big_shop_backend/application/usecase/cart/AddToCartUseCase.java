package com.esia.big_shop_backend.application.usecase.cart;

import com.esia.big_shop_backend.application.usecase.cart.command.AddToCartCommand;
import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.CartRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.service.CartService;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final GetOrCreateCartUseCase getOrCreateCartUseCase;
    private final CartService cartService;

    @Transactional
    public Cart execute(AddToCartCommand command) {
        Product product = productRepository.findById(ProductId.of(command.getProductId()))
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getStockQuantity() < command.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        Cart cart = getOrCreateCartUseCase.execute(UserId.of(command.getUserId()));
        cartService.addItem(cart, product, command.getQuantity());

        return cartRepository.save(cart);
    }
}
