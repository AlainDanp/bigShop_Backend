package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.cart.*;
import com.esia.big_shop_backend.application.usecase.cart.command.AddToCartCommand;
import com.esia.big_shop_backend.application.usecase.cart.command.ClearCartCommand;
import com.esia.big_shop_backend.application.usecase.cart.command.RemoveFromCartCommand;
import com.esia.big_shop_backend.application.usecase.cart.command.UpdateCartItemCommand;
import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.infrastrucute.sercutity.CustomUserDetails;
import com.esia.big_shop_backend.presentation.dto.request.cart.AddToCartRequest;
import com.esia.big_shop_backend.presentation.dto.request.cart.UpdateCartItemRequest;
import com.esia.big_shop_backend.presentation.dto.response.cart.CartResponse;
import com.esia.big_shop_backend.presentation.mapper.CartRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Cart management APIs")
public class CartController {

    private final GetOrCreateCartUseCase getOrCreateCartUseCase;
    private final AddToCartUseCase addToCartUseCase;
    private final UpdateCartItemUseCase updateCartItemUseCase;
    private final RemoveFromCartUseCase removeFromCartUseCase;
    private final ClearCartUseCase clearCartUseCase;
    private final CartRestMapper cartRestMapper;

    @GetMapping
    @Operation(summary = "Get current user's cart")
    public ResponseEntity<CartResponse> getCart(@org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserId();
        Cart cart = getOrCreateCartUseCase.execute(UserId.of(userId));
        return ResponseEntity.ok(cartRestMapper.toResponse(cart));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<CartResponse> addToCart(
            @org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody AddToCartRequest request
    ) {
        Long userId = user.getUserId();
        AddToCartCommand command = new AddToCartCommand(userId, request.getProductId(), request.getQuantity());
        Cart cart = addToCartUseCase.execute(command);
        return ResponseEntity.ok(cartRestMapper.toResponse(cart));
    }

    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartResponse> updateCartItem(
            @org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        Long userId = user.getUserId();
        UpdateCartItemCommand command = new UpdateCartItemCommand(userId, itemId, request.getQuantity());
        Cart cart = updateCartItemUseCase.execute(command);
        return ResponseEntity.ok(cartRestMapper.toResponse(cart));
    }

    @DeleteMapping
    @Operation(summary = "Clear cart")
    public ResponseEntity<Map<String, String>> clearCart(
            @org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();
        ClearCartCommand command = new ClearCartCommand(userId);
        clearCartUseCase.execute(command);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Cart cleared successfully");
        return ResponseEntity.ok(response);
    }
}
