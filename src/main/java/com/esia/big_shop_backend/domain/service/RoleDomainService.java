package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Role;
import com.esia.big_shop_backend.domain.valueobject.enums.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public class RoleDomainService {

    public boolean isAdminRole(Role role) {
        return role.getName() == RoleEnum.ADMIN;
    }

    public boolean isUserRole(Role role) {
        return role.getName() == RoleEnum.USER;
    }

    public boolean isModeratorRole(Role role) {
        return role.getName() == RoleEnum.MODERATOR;
    }

    public boolean hasElevatedPrivileges(Role role) {
        return role.getName() == RoleEnum.ADMIN || role.getName() == RoleEnum.MODERATOR;
    }

    public int getRolePriority(Role role) {
        return switch (role.getName()) {
            case ADMIN -> 3;
            case MODERATOR -> 2;
            case USER -> 1;
        };
    }
}
