package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.AddressMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.AddressJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJpaRepository jpaRepository;
    private final AddressMapper mapper = new AddressMapper();

    @Override
    public Address save(Address address) {
        var saved = jpaRepository.save(mapper.toEntity(address));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Address> findByIdAndUserId(Long id, Long userId) {
        return jpaRepository.findByIdAndUserId(id, userId).map(mapper::toDomain);
    }

    @Override
    public List<Address> findAllByUserId(Long userId) {
        return jpaRepository.findAllByUserId(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Address> findById(Long userId) {
        return jpaRepository.findById(userId).map(mapper::toDomain);
    }

    @Override
    public Optional<Address> findDefaultByUserId(Long userId) {
        return jpaRepository.findFirstByUserIdAndIsDefaultTrue(userId).map(mapper::toDomain);
    }

    @Override
    public void unsetDefaultForUser(Long userId) {
        var all = jpaRepository.findAllByUserId(userId);
        for (var a : all) {
            if (a.isDefault()) {
                a.setDefault(false);
                jpaRepository.save(a);
            }
        }
    }

    @Override
    public void deleteById(Long userId) {
        jpaRepository.deleteById(userId);
    }

    @Override
    public void deleteByIdAndUserId(Long id, Long userId) {
        jpaRepository.deleteByIdAndUserId(id, userId);
    }
}
