package com.musinsa.snap.outfit.domain.brand.service;

import com.musinsa.snap.outfit.common.error.CoreException;
import com.musinsa.snap.outfit.domain.brand.dto.CreateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.dto.GetBrandListQuery;
import com.musinsa.snap.outfit.domain.brand.dto.UpdateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.error.BrandErrorCode;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.repository.BrandRepository;
import com.musinsa.snap.outfit.domain.common.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public Brand getBrand(Long brandId) {
        return brandRepository.get(brandId)
            .orElseThrow(() -> new CoreException(BrandErrorCode.BRAND_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public PageResult<Brand> getBrandList(GetBrandListQuery query) {
        return brandRepository.getList(query);
    }

    @Transactional
    public Brand createBrand(CreateBrandCommand command) {
        Brand brand = new Brand(command);
        this.validateBrandNameNotExists(command.getBrandName());
        return brandRepository.save(brand);
    }

    @Transactional
    public Brand updateBrand(UpdateBrandCommand command) {
        Brand brand = this.getBrand(command.getBrandId());

        String newName = command.getBrandName();
        if (!brand.getBrandName().equals(newName)) {
            validateBrandNameNotExists(newName);
        }

        brand.update(command.getBrandName());
        return brand;
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = this.getBrand(brandId);
        brand.delete();
    }

    @Transactional(readOnly = true)
    public void validateBrandNameNotExists(String brandName) {
        if (brandRepository.existsByBrandName(brandName)) {
            throw new CoreException(BrandErrorCode.BRAND_NAME_ALREADY_EXISTS);
        }
    }
}
