package com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa;

import com.esia.big_shop_backend.infrastrucute.persitence.entity.AddressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressJpaRepository extends JpaRepository<AddressJpaEntity, Long> {
    Optional<AddressJpaEntity> findByIdAndUserId(Long id, Long userId);
    List<AddressJpaEntity> findAllByUserId(Long userId);
    Optional<AddressJpaEntity> findFirstByUserIdAndIsDefaultTrue(Long userId);
    void deleteByIdAndUserId(Long id, Long userId);
}
