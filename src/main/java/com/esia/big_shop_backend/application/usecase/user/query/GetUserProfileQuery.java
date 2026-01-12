package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserProfileQuery {
    private final Long userId;
}
