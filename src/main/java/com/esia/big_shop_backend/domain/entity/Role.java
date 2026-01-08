package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.enums.RoleEnum;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Role {
    private final RoleId id;
    private final RoleEnum name;
    private final String description;
}
