package com.esia.big_shop_backend.domain.event;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.CartId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartUpdatedEvent {
    private Long cartId;
    private Long userId;
    private Double newTotalAmount;
    private String action; // e.g., "ADD_ITEM", "UPDATE_ITEM", "REMOVE_ITEM"
    private LocalDateTime occurredOn;

    public static CartUpdatedEvent of(CartId cartId, UserId userId, Money newTotalAmount, String action) {
        return new CartUpdatedEvent(
                cartId.getValue(),
                userId.getValue(),
                newTotalAmount.getAmount(),
                action,
                LocalDateTime.now()
        );
    }
}
