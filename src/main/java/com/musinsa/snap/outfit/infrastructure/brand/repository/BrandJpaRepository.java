package com.musinsa.snap.outfit.infrastructure.brand.repository;

import com.musinsa.snap.outfit.domain.brand.model.Brand;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByBrandIdAndDeletedIsFalse(Long id);
    boolean existsByBrandNameAndDeletedIsFalse(String brandName);
    Page<Brand> findAllByDeletedIsFalse(Pageable pageable);
}
