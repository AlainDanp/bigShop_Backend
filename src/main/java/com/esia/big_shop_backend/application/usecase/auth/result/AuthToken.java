package com.esia.big_shop_backend.application.usecase.auth.result;

import com.esia.big_shop_backend.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthToken {
    private final String accessToken;
    @Builder.Default
    private final String tokenType = "Bearer";
    private final User user;
}
