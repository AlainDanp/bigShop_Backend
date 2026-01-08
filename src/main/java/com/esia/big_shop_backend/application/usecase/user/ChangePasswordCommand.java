package com.esia.big_shop_backend.application.usecase.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordCommand {
    private final Long userId;
    private final String currentPassword;
    private final String newPassword;
}
