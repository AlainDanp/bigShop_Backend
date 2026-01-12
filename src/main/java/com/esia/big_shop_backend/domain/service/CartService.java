package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.entity.CartItem;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.Money;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    public void addItem(Cart cart, Product product, int quantity) {
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // Note: CartItemId generation should ideally be handled by persistence or a factory
            // For now, we assume it's handled when persisting or we use a temporary ID if needed.
            cart.getItems().add(new CartItem(null, product, quantity, product.getPrice()));
        }
    }

    public void removeItem(Cart cart, Product product) {
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(product.getId()));
    }

    public void updateItemQuantity(Cart cart, Product product, int quantity) {
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            if (quantity <= 0) {
                removeItem(cart, product);
            } else {
                existingItem.get().setQuantity(quantity);
            }
        }
    }

    public void clear(Cart cart) {
        cart.getItems().clear();
    }

    public Money getTotalPrice(Cart cart) {
        return cart.getItems().stream()
                .map(CartItem::getSubTotal)
                .reduce(Money.ZERO, Money::add);
    }
}
