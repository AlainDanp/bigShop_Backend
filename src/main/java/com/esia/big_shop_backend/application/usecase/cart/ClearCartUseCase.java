package com.esia.big_shop_backend.application.usecase.cart;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.usecase.cart.command.ClearCartCommand;
import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.event.CartUpdatedEvent;
import com.esia.big_shop_backend.domain.repository.CartRepository;
import com.esia.big_shop_backend.domain.service.CartService;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClearCartUseCase {

    private final CartRepository cartRepository;
    private final CartService cartService;
    private final EventPublisher eventPublisher;

    @Transactional
    public void execute(ClearCartCommand command) {
        Cart cart = cartRepository.findByUserId(UserId.of(command.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        
        cartService.clear(cart);
        Cart savedCart = cartRepository.save(cart);

        eventPublisher.publish(CartUpdatedEvent.of(
                savedCart.getId(),
                savedCart.getUserId(),
                cartService.getTotalPrice(savedCart),
                "CLEAR_CART"
        ));
    }
}
