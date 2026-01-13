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
public class ProductDeletedEvent {
    private Long productId;
    private LocalDateTime occurredOn;

    public static ProductDeletedEvent of(ProductId productId) {
        return new ProductDeletedEvent(productId.getValue(), LocalDateTime.now());
    }
}
