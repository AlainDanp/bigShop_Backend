package com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa;

import com.esia.big_shop_backend.infrastrucute.persitence.entity.CategoryJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, Long> {
    boolean existsByName(String name);
    
    Page<CategoryJpaEntity> findByParentIsNull(Pageable pageable);
    
    Page<CategoryJpaEntity> findByParentId(Long parentId, Pageable pageable);
}
