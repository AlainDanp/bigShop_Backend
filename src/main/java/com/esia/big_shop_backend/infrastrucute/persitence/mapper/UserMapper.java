package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.Password;
import com.esia.big_shop_backend.domain.valueobject.PersonalInfo;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.RoleJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) return null;

        PersonalInfo personalInfo = new PersonalInfo(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDateOfBirth(),
                entity.getAvatarUrl()
        );

        Set<RoleId> roleIds = entity.getRoles() != null
                ? entity.getRoles().stream()
                .map(role -> RoleId.of(role.getId()))
                .collect(Collectors.toSet())
                : new HashSet<>();

        Set<com.esia.big_shop_backend.domain.entity.Role> roles = entity.getRoles() != null
                ? entity.getRoles().stream()
                .map(roleMapper::toDomain)
                .collect(Collectors.toSet())
                : new HashSet<>();

        return new User(
                entity.getId() != null ? UserId.of(entity.getId()) : null,
                Username.of(entity.getUsername()),
                new Email(entity.getEmail()),
                new Password(entity.getPassword()),
                personalInfo,
                roleIds,
                roles,
                entity.getIsActive(),
                entity.getEmailVerified(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public UserJpaEntity toJpaEntity(User domain) {
        if (domain == null) return null;

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domain.getId() != null ? domain.getId().getValue() : null);
        entity.setUsername(domain.getUsername().getValue());
        entity.setEmail(domain.getEmail().getValue());
        entity.setPassword(domain.getPassword().getValue());

        if (domain.getPersonalInfo() != null) {
            entity.setFirstName(domain.getPersonalInfo().getFirstName());
            entity.setLastName(domain.getPersonalInfo().getLastName());
            entity.setDateOfBirth(domain.getPersonalInfo().getDateOfBirth());
            entity.setAvatarUrl(domain.getPersonalInfo().getAvatar());
        }

        entity.setIsActive(domain.isActive());
        entity.setEmailVerified(domain.isEmailVerified());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setRoles(new HashSet<>());

        return entity;
    }
}
