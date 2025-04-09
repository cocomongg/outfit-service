package com.musinsa.snap.outfit.application.brand;

import com.musinsa.snap.outfit.domain.common.PageResult;
import com.musinsa.snap.outfit.domain.brand.dto.GetBrandListQuery;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetBrandListUseCase {

    private final BrandService brandService;

    public PageResult<Brand> execute(GetBrandListQuery query) {
        return brandService.getBrandList(query);
    }
}
