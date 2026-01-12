package com.esia.big_shop_backend.application.usecase.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteUserAccountCommand {
    private final Long userId;
}
