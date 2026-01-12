package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    Address save(Address address);

    Optional<Address> findByIdAndUserId(Long id, Long userId);

    List<Address> findAllByUserId(Long userId);

    Optional<Address> findDefaultByUserId(Long userId);

    void unsetDefaultForUser(Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
}
