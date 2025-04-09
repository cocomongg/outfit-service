package com.musinsa.snap.outfit.interfaces.api.brand.mapper;

import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.domain.brand.dto.CreateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.dto.GetBrandListQuery;
import com.musinsa.snap.outfit.domain.brand.dto.UpdateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.CreateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.GetBrandListRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.UpdateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandListResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public CreateBrandCommand toCreateBrandCommand(CreateBrandRequest request) {
        return new CreateBrandCommand(request.getBrandName());
    }

    public UpdateBrandCommand toUpdateBrandCommand(Long brandId, UpdateBrandRequest request) {
        return new UpdateBrandCommand(brandId, request.getBrandName());
    }

    public GetBrandListQuery toGetBrandListQuery(GetBrandListRequest request) {
        return new GetBrandListQuery(request.getPageNo(), request.getPageSize());
    }

    public BrandCreateResponse toBrandCreateResponse(Brand brand) {
        return new BrandCreateResponse(brand.getBrandId());
    }

    public BrandListResponse toBrandListResponse(PageResult<Brand> brandPage) {
        List<BrandInfo> brandList = new ArrayList<>();
        for (Brand brand : brandPage.getContent()) {
            BrandInfo brandInfo = new BrandInfo(
                brand.getBrandId(),
                brand.getBrandName());
            brandList.add(brandInfo);
        }
        return new BrandListResponse(
            brandPage.getPageNo(),
            brandPage.getPageSize(),
            brandPage.getTotalElements(),
            brandPage.getTotalPages(),
            brandList);
    }

    public BrandDetailResponse toBrandDetailResponse(Brand brand) {
        return new BrandDetailResponse(
            brand.getBrandId(),
            brand.getBrandName(),
            brand.getCreatedAt());
    }
}
