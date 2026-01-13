package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Category {
    private final CategoryId id;
    private Long parentId;
    private String name;
    private String description;
    private boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
