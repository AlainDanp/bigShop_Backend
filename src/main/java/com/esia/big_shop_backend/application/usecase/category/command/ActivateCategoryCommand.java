package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivateCategoryCommand {
    private final Long categoryId;
}
