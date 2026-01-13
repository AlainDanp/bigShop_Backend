package com.esia.big_shop_backend.presentation.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserAccountRequest {
    private Long userId;
}
