package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.cart.*;
import com.esia.big_shop_backend.application.usecase.cart.command.AddToCartCommand;
import com.esia.big_shop_backend.application.usecase.cart.command.ClearCartCommand;
import com.esia.big_shop_backend.application.usecase.cart.command.RemoveFromCartCommand;
import com.esia.big_shop_backend.application.usecase.cart.command.UpdateCartItemCommand;
import com.esia.big_shop_backend.application.usecase.cart.query.GetOrCreateCartQuery;
import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.presentation.dto.request.cart.AddToCartRequest;
import com.esia.big_shop_backend.presentation.dto.request.cart.UpdateCartItemRequest;
import com.esia.big_shop_backend.presentation.dto.response.cart.CartResponse;
import com.esia.big_shop_backend.presentation.mapper.CartRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        Cart cart = getOrCreateCartUseCase.execute(UserId.of(userId));
        return ResponseEntity.ok(cartRestMapper.toResponse(cart));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<CartResponse> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AddToCartRequest request) {
        Long userId = getUserIdFromUserDetails(userDetails);
        AddToCartCommand command = new AddToCartCommand(userId, request.getProductId(), request.getQuantity());
        Cart cart = addToCartUseCase.execute(command);
        return ResponseEntity.ok(cartRestMapper.toResponse(cart));
    }

    @PutMapping("/items/{productId}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartResponse> updateCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        Long userId = getUserIdFromUserDetails(userDetails);
        UpdateCartItemCommand command = new UpdateCartItemCommand(userId, productId, request.getQuantity());
        Cart cart = updateCartItemUseCase.execute(command);
        return ResponseEntity.ok(cartRestMapper.toResponse(cart));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<CartResponse> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId) {
        Long userId = getUserIdFromUserDetails(userDetails);
        RemoveFromCartCommand command = new RemoveFromCartCommand(userId, productId);
        Cart cart = removeFromCartUseCase.execute(command);
        return ResponseEntity.ok(cartRestMapper.toResponse(cart));
    }

    @DeleteMapping
    @Operation(summary = "Clear cart")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        ClearCartCommand command = new ClearCartCommand(userId);
        clearCartUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }

    // Helper method to extract user ID from UserDetails
    // In a real application, UserDetails might be a custom implementation containing the ID directly
    // Or you might look up the user by username
    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        // This is a placeholder. You should adapt this to your actual UserDetails implementation.
        // For example, if your UserDetails is an instance of your User entity or a wrapper around it:
        // return ((CustomUserDetails) userDetails).getId();

        // If you only have the username, you might need a UserService to look up the ID,
        // or change the UseCases to accept username instead of ID.
        // For now, assuming the username IS the ID (which is rare) or this needs to be implemented:
        
        // TODO: Implement proper user ID extraction
        // For demonstration, throwing an exception if not implemented correctly
        throw new UnsupportedOperationException("User ID extraction from UserDetails not implemented yet. Please adapt based on your security configuration.");
    }
}
