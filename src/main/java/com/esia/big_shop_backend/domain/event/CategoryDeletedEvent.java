package com.esia.big_shop_backend.domain.event;

import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDeletedEvent {
    private Long categoryId;
    private LocalDateTime occurredOn;

    public static CategoryDeletedEvent of(CategoryId categoryId) {
        return new CategoryDeletedEvent(categoryId.getValue(), LocalDateTime.now());
    }
}
