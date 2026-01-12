package com.esia.big_shop_backend.application.usecase.cart;

import com.esia.big_shop_backend.application.usecase.cart.query.GetCartTotalQuery;
import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.repository.CartRepository;
import com.esia.big_shop_backend.domain.service.CartService;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCartTotalUseCase {

    private final CartRepository cartRepository;
    private final CartService cartService;

    @Transactional(readOnly = true)
    public Money execute(GetCartTotalQuery query) {
        Cart cart = cartRepository.findByUserId(UserId.of(query.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        
        return cartService.getTotalPrice(cart);
    }
}
