package com.esia.big_shop_backend.application.usecase.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUserAccountCommand {
    private final Long userId;
}
