package com.musinsa.snap.outfit.application.brand;

import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetBrandDetailUseCase {

    private final BrandService brandService;

    public Brand execute(Long id) {
        return brandService.getBrand(id);
    }
}
