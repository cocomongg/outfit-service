package com.musinsa.snap.outfit.domain.brand.repository;

import com.musinsa.snap.outfit.domain.common.PageResult;
import com.musinsa.snap.outfit.domain.brand.dto.GetBrandListQuery;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import java.util.Optional;

public interface BrandRepository {
    Brand save(Brand brand);
    Optional<Brand> get(Long id);
    PageResult<Brand> getList(GetBrandListQuery query);
    boolean existsByBrandName(String brandName);
}
