package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.Password;
import com.esia.big_shop_backend.domain.valueobject.PersonalInfo;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private final UserId id;
    private Username username;
    private Email email;
    private Password password;
    private PersonalInfo personalInfo;
    private Set<RoleId> roleIds;
    private Set<Role> roles;
    private boolean active;
    private boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
