package com.esia.big_shop_backend.application.usecase.user.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordCommand {
    public Long getUserId() {
        return null;
    }
    private String oldPassword;
    private String newPassword;
    private String currentPassword;
}
