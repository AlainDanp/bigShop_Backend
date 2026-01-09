package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderItemId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.OrderItemJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem toDomain(OrderItemJpaEntity entity) {
        if (entity == null) return null;

        return new OrderItem(
                entity.getId() != null ? OrderItemId.of(entity.getId()) : null,
                ProductId.of(entity.getProductId()),
                entity.getProductName(),
                new Money(entity.getUnitPrice(), entity.getCurrency()),
                entity.getQuantity()
        );
    }

    public OrderItemJpaEntity toJpaEntity(OrderItem domain) {
        if (domain == null) return null;

        return new OrderItemJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                null, // orderId will be set by the Order relationship
                domain.getProductId().getValue(),
                domain.getProductName(),
                domain.getUnitPrice().getAmount(),
                domain.getUnitPrice().getCurrency(),
                domain.getQuantity(),
                null  // order relation
        );
    }
}
