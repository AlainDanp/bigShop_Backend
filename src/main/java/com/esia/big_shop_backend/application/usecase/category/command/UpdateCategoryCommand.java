package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCategoryCommand {
    private final Long categoryId;
    private final String name;
    private final String description;
}
