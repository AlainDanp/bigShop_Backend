package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginCommand {
    private final String email;
    private final String password;
}
