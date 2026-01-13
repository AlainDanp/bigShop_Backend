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
public class CategoryCreatedEvent {
    private Long categoryId;
    private String name;
    private LocalDateTime occurredOn;

    public static CategoryCreatedEvent of(CategoryId categoryId, String name) {
        return new CategoryCreatedEvent(categoryId.getValue(), name, LocalDateTime.now());
    }
}
