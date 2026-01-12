package com.esia.big_shop_backend.application.usecase.cart;

import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.repository.CartRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GetOrCreateCartUseCase {

    private final CartRepository cartRepository;

    @Transactional
    public Cart execute(UserId userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(null, userId, new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }
}
