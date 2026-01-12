package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.CartItem;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.ids.CartItemId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartItemJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapper {

    private final ProductMapper productMapper;

    public CartItem toDomain(CartItemJpaEntity entity) {
        if (entity == null) return null;

        Product product = productMapper.toDomain(entity.getProduct());
        return new CartItem(
                CartItemId.of(entity.getId()),
                product,
                entity.getQuantity(),
                product.getPrice()
        );
    }

    public CartItemJpaEntity toJpaEntity(CartItem domain, CartJpaEntity cartEntity) {
        if (domain == null) return null;

        CartItemJpaEntity entity = new CartItemJpaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        entity.setCart(cartEntity);
        entity.setProduct(productMapper.toJpaEntity(domain.getProduct()));
        entity.setQuantity(domain.getQuantity());
        return entity;
    }
}
