package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCategoryCommand {
    private final String name;
    private final Long parentId;
    private final String description;
}
