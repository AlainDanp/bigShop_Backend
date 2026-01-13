package com.esia.big_shop_backend.application.usecase.cart;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.usecase.cart.command.RemoveFromCartCommand;
import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.event.CartUpdatedEvent;
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
public class RemoveFromCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final EventPublisher eventPublisher;

    @Transactional
    public Cart execute(RemoveFromCartCommand command) {
        Cart cart = cartRepository.findByUserId(UserId.of(command.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        Product product = productRepository.findById(ProductId.of(command.getProductId()))
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        cartService.removeItem(cart, product);

        Cart savedCart = cartRepository.save(cart);

        eventPublisher.publish(CartUpdatedEvent.of(
                savedCart.getId(),
                savedCart.getUserId(),
                cartService.getTotalPrice(savedCart),
                "REMOVE_ITEM"
        ));

        return savedCart;
    }
}
