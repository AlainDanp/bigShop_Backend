package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.valueobject.PhoneNumber;
import com.esia.big_shop_backend.domain.valueobject.ShippingInfo;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.OrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public Order toDomain(OrderJpaEntity entity) {
        if (entity == null) return null;

        ShippingInfo shippingInfo = new ShippingInfo(
                entity.getShippingFullName(),
                PhoneNumber.of(entity.getShippingPhone()),
                entity.getShippingAddress()
        );

        List<OrderItem> items = entity.getItems() != null
                ? entity.getItems().stream()
                .map(orderItemMapper::toDomain)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return new Order(
                entity.getId() != null ? OrderId.of(entity.getId()) : null,
                entity.getOrderNumber(),
                UserId.of(entity.getUserId()),
                items,
                mapToDomainStatus(entity.getStatus()),
                entity.getTotalItems() != null ? entity.getTotalItems() : 0,
                entity.getTotalAmount() != null ? entity.getTotalAmount() : 0,
                shippingInfo,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public OrderJpaEntity toJpaEntity(Order domain) {
        if (domain == null) return null;

        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(domain.getId() != null ? domain.getId().getValue() : null);
        entity.setOrderNumber(domain.getOrderNumber());
        entity.setUserId(domain.getUserId().getValue());
        entity.setStatus(mapToJpaStatus(domain.getStatus()));

        if (domain.getShippingInfo() != null) {
            entity.setShippingFullName(domain.getShippingInfo().getFullName());
            entity.setShippingPhone(domain.getShippingInfo().getPhoneNumber().getValue());
            entity.setShippingAddress(domain.getShippingInfo().getAddressText());
        }

        entity.setTotalItems(domain.getTotalItems());
        entity.setTotalAmount(domain.getTotalAmount());

        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        if (domain.getItems() != null) {
            entity.setItems(domain.getItems().stream()
                    .map(orderItemMapper::toJpaEntity)
                    .collect(Collectors.toList()));
        }

        return entity;
    }

    private OrderStatus mapToDomainStatus(OrderJpaEntity.OrderStatus jpaStatus) {
        if (jpaStatus == null) return null;
        return switch (jpaStatus) {
            case PENDING -> OrderStatus.PENDING;
            case CONFIRMED -> OrderStatus.CONFIRMED;
            case PROCESSING -> OrderStatus.PROCESSING;
            case SHIPPED -> OrderStatus.SHIPPED;
            case DELIVERED -> OrderStatus.DELIVERED;
            case CANCELLED -> OrderStatus.CANCELLED;
        };
    }

    private OrderJpaEntity.OrderStatus mapToJpaStatus(OrderStatus domainStatus) {
        if (domainStatus == null) return null;
        return switch (domainStatus) {
            case PENDING -> OrderJpaEntity.OrderStatus.PENDING;
            case CONFIRMED -> OrderJpaEntity.OrderStatus.CONFIRMED;
            case PROCESSING -> OrderJpaEntity.OrderStatus.PROCESSING;
            case SHIPPED -> OrderJpaEntity.OrderStatus.SHIPPED;
            case DELIVERED -> OrderJpaEntity.OrderStatus.DELIVERED;
            case CANCELLED -> OrderJpaEntity.OrderStatus.CANCELLED;
        };
    }
}
