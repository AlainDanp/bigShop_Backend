package com.esia.big_shop_backend.presentation.dto.request.user;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignRoleRequest {
    private Long userId;
    private Long roleId;
}