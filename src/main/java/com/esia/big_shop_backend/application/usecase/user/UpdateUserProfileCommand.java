package com.esia.big_shop_backend.application.usecase.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserProfileCommand {
    private final Long userId;
    private final String firstName;
    private final String lastName;
}
