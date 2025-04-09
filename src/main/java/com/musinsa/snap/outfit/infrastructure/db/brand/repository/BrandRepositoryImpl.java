package com.musinsa.snap.outfit.infrastructure.db.brand.repository;

import com.musinsa.snap.outfit.domain.brand.dto.GetBrandListQuery;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.repository.BrandRepository;
import com.musinsa.snap.outfit.domain.common.model.PageResult;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Brand save(Brand brand) {
        return brandJpaRepository.save(brand);
    }

    @Override
    public Optional<Brand> get(Long id) {
        return brandJpaRepository.findByBrandIdAndDeletedIsFalse(id);
    }

    @Override
    public PageResult<Brand> getList(GetBrandListQuery query) {
        Sort createdAtDescSort = Sort.by(Direction.DESC, "createdAt");
        PageRequest pageable = PageRequest.of(query.getPageNo(), query.getPageSize(), createdAtDescSort);
        Page<Brand> pageBrand = brandJpaRepository.findAllByDeletedIsFalse(pageable);
        return new PageResult<>(
                pageBrand.getNumber(),
                pageBrand.getSize(),
                pageBrand.getTotalElements(),
                pageBrand.getTotalPages(),
                pageBrand.getContent()
        );
    }

    @Override
    public boolean existsByBrandName(String brandName) {
        return brandJpaRepository.existsByBrandNameAndDeletedIsFalse(brandName);
    }
}
