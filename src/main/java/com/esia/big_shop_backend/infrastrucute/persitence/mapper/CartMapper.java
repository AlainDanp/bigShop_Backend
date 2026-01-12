package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.entity.CartItem;
import com.esia.big_shop_backend.domain.valueobject.ids.CartId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartItemJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public Cart toDomain(CartJpaEntity entity) {
        if (entity == null) return null;

        List<CartItem> items = entity.getItems().stream()
                .map(cartItemMapper::toDomain)
                .collect(Collectors.toList());

        Cart cart = new Cart(
                CartId.of(entity.getId()),
                UserId.of(entity.getUserId()),
                items
        );
        return cart;
    }

    public CartJpaEntity toJpaEntity(Cart domain) {
        if (domain == null) return null;

        CartJpaEntity entity = new CartJpaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        entity.setUserId(domain.getUserId().getValue());

        if (domain.getItems() != null) {
            List<CartItemJpaEntity> itemEntities = domain.getItems().stream()
                    .map(item -> cartItemMapper.toJpaEntity(item, entity))
                    .collect(Collectors.toList());
            entity.setItems(itemEntities);
        }

        return entity;
    }
}
