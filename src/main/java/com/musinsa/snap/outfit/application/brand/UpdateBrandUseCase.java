package com.musinsa.snap.outfit.application.brand;

import com.musinsa.snap.outfit.domain.brand.dto.UpdateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateBrandUseCase {

    private final BrandService brandService;

    public Brand execute(UpdateBrandCommand command) {
        return brandService.updateBrand(command);
    }
}
