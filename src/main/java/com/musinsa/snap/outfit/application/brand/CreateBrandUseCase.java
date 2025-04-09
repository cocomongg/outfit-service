package com.musinsa.snap.outfit.application.brand;

import com.musinsa.snap.outfit.domain.brand.dto.CreateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateBrandUseCase {
    private final BrandService brandService;

    public Brand execute(CreateBrandCommand command) {
        return brandService.createBrand(command);
    }
}
