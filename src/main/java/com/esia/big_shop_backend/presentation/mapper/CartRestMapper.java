package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.entity.CartItem;
import com.esia.big_shop_backend.domain.service.CartService;
import com.esia.big_shop_backend.presentation.dto.response.cart.CartItemResponse;
import com.esia.big_shop_backend.presentation.dto.response.cart.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartRestMapper {

    private final CartService cartService;

    public CartResponse toResponse(Cart cart) {
        if (cart == null) {
            return null;
        }

        List<CartItemResponse> itemResponses = cart.getItems() != null
                ? cart.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();

        BigDecimal totalPrice = BigDecimal.valueOf(cartService.getTotalPrice(cart).getAmount());

        return CartResponse.builder()
                .id(cart.getId() != null ? cart.getId().getValue() : null)
                .userId(cart.getUserId() != null ? cart.getUserId().getValue() : null)
                .items(itemResponses)
                .totalPrice(totalPrice)
                .build();
    }

    private CartItemResponse toItemResponse(CartItem item) {
        if (item == null) {
            return null;
        }

        return CartItemResponse.builder()
                .id(item.getId() != null ? item.getId().getValue() : null)
                .productId(item.getProduct().getId().getValue())
                .productName(item.getProduct().getName())
                // .productImageUrl(item.getProduct().getImageUrl()) // Assuming image URL might be added later or fetched differently
                .quantity(item.getQuantity())
                .price(BigDecimal.valueOf(item.getPrice().getAmount()))
                .subTotal(BigDecimal.valueOf(item.getSubTotal().getAmount()))
                .build();
    }
}
