package com.esia.big_shop_backend.presentation.dto.response.auth;

import com.esia.big_shop_backend.presentation.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private UserResponse user;
}
