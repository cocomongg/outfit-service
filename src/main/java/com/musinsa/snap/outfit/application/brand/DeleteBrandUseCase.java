package com.musinsa.snap.outfit.application.brand;

import com.musinsa.snap.outfit.domain.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteBrandUseCase {

    private final BrandService brandService;

    public void execute(Long id) {
        brandService.deleteBrand(id);
    }
}
