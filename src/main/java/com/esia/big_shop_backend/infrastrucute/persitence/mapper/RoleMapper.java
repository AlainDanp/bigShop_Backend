package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.Role;
import com.esia.big_shop_backend.domain.valueobject.enums.RoleEnum;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.RoleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toDomain(RoleJpaEntity entity) {
        if (entity == null) return null;

        return new Role(
                entity.getId() != null ? RoleId.of(entity.getId()) : null,
                mapToDomainEnum(entity.getName()),
                entity.getDescription()
        );
    }

    public RoleJpaEntity toJpaEntity(Role domain) {
        if (domain == null) return null;

        return new RoleJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                mapToJpaEnum(domain.getName()),
                domain.getDescription()
        );
    }

    private RoleEnum mapToDomainEnum(RoleJpaEntity.RoleName jpaName) {
        if (jpaName == null) return null;
        return switch (jpaName) {
            case ADMIN -> RoleEnum.ADMIN;
            case USER -> RoleEnum.USER;
            case MODERATOR -> RoleEnum.MODERATOR;
        };
    }

    private RoleJpaEntity.RoleName mapToJpaEnum(RoleEnum domainName) {
        if (domainName == null) return null;
        return switch (domainName) {
            case ADMIN -> RoleJpaEntity.RoleName.ADMIN;
            case USER -> RoleJpaEntity.RoleName.USER;
            case MODERATOR -> RoleJpaEntity.RoleName.MODERATOR;
        };
    }
}
