package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForgotPasswordCommand {
    private final String email;
}
