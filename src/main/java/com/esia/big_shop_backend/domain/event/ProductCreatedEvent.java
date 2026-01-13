package com.esia.big_shop_backend.domain.event;

import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductCreatedEvent {
    private Long productId;
    private String name;
    private LocalDateTime occurredOn;

    public static ProductCreatedEvent of(ProductId productId, String name) {
        return new ProductCreatedEvent(productId.getValue(), name, LocalDateTime.now());
    }
}
