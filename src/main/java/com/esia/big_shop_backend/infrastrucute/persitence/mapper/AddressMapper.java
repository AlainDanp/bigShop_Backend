package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.AddressJpaEntity;

public class AddressMapper {

    public Address toDomain(AddressJpaEntity e) {
        if (e == null) return null;
        return Address.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .fullName(e.getFullName())
                .phone(e.getPhone())
                .street(e.getStreet())
                .city(e.getCity())
                .zipCode(e.getZipCode())
                .country(e.getCountry())
                .isDefault(e.isDefault())
                .build();
    }

    public AddressJpaEntity toEntity(Address a) {
        if (a == null) return null;
        AddressJpaEntity e = new AddressJpaEntity();
        e.setId(a.getId());
        e.setUserId(a.getUserId());
        e.setFullName(a.getFullName());
        e.setPhone(a.getPhone());
        e.setStreet(a.getStreet());
        e.setCity(a.getCity());
        e.setZipCode(a.getZipCode());
        e.setCountry(a.getCountry());
        e.setDefault(a.isDefault());
        return e;
    }
}
