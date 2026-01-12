package com.esia.big_shop_backend.application.usecase.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAvatarCommand {
    private final Long userId;
    private final String avatarUrl;
}
