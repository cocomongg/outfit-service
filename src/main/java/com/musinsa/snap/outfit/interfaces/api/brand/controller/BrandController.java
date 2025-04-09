package com.musinsa.snap.outfit.interfaces.api.brand.controller;

import static com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.CategoryWithGoodsInfo;

import com.musinsa.snap.outfit.application.brand.CreateBrandUseCase;
import com.musinsa.snap.outfit.application.brand.DeleteBrandUseCase;
import com.musinsa.snap.outfit.application.brand.GetBrandDetailUseCase;
import com.musinsa.snap.outfit.application.brand.GetBrandListUseCase;
import com.musinsa.snap.outfit.application.brand.UpdateBrandUseCase;
import com.musinsa.snap.outfit.domain.brand.dto.CreateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.dto.GetBrandListQuery;
import com.musinsa.snap.outfit.domain.brand.dto.UpdateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.common.PageResult;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.CreateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.GetBrandListRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.UpdateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandListResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.SingleBrandLowestPriceResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.mapper.BrandMapper;
import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsInfo;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
@RestController
public class BrandController implements BrandControllerDocs{

    private final CreateBrandUseCase createBrandUseCase;
    private final GetBrandDetailUseCase getBrandDetailUseCase;
    private final GetBrandListUseCase getBrandListUseCase;
    private final UpdateBrandUseCase updateBrandUseCase;
    private final DeleteBrandUseCase deleteBrandUseCase;

    private final BrandMapper brandMapper;

    @Override
    @PostMapping
    public ApiSuccessResponse<BrandCreateResponse> createBrand(@Valid @RequestBody CreateBrandRequest request) {
        CreateBrandCommand command = brandMapper.toCreateBrandCommand(request);
        Brand brand = createBrandUseCase.execute(command);
        BrandCreateResponse response = brandMapper.toBrandCreateResponse(brand);

        return ApiSuccessResponse.CREATED(response);
    }

    @Override
    @GetMapping
    public ApiSuccessResponse<BrandListResponse> getBrandList(@Valid GetBrandListRequest request) {
        GetBrandListQuery query = brandMapper.toGetBrandListQuery(request);
        PageResult<Brand> brandPage = getBrandListUseCase.execute(query);
        BrandListResponse response = brandMapper.toBrandListResponse(brandPage);

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @GetMapping("/{brandId}")
    public ApiSuccessResponse<BrandDetailResponse> getBrandDetail(@PathVariable Long brandId) {
        Brand brand = getBrandDetailUseCase.execute(brandId);
        BrandDetailResponse response = brandMapper.toBrandDetailResponse(brand);

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @PutMapping("/{brandId}")
    public ApiSuccessResponse<BrandDetailResponse> updateBrand(@PathVariable Long brandId,
        @Valid @RequestBody UpdateBrandRequest request) {
        UpdateBrandCommand command = brandMapper.toUpdateBrandCommand(brandId, request);
        Brand brand = updateBrandUseCase.execute(command);
        BrandDetailResponse response = brandMapper.toBrandDetailResponse(brand);

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @DeleteMapping("/{brandId}")
    public ApiSuccessResponse<?> deleteBrand(@PathVariable Long brandId) {
        deleteBrandUseCase.execute(brandId);

        return ApiSuccessResponse.OK();
    }

    @Override
    @GetMapping("/single/lowest-price")
    public ApiSuccessResponse<SingleBrandLowestPriceResponse> getBrandLowestPrice() {
        List<GoodsInfo> goodsList = new ArrayList<>();
        goodsList.add(new GoodsInfo(1L, "상품1", 5000L));
        goodsList.add(new GoodsInfo(2L, "상품2", 8000L));

        // 임의의 카테고리별 상품 정보를 생성
        List<CategoryWithGoodsInfo> categories = new ArrayList<>();
        categories.add(new CategoryWithGoodsInfo(1L, "상의", goodsList));
        categories.add(new CategoryWithGoodsInfo(2L, "하의", goodsList));

        // 임의의 단일 브랜드 최저 가격 정보 생성
        SingleBrandLowestPriceResponse response =
            new SingleBrandLowestPriceResponse(1L, "A", categories);

        return ApiSuccessResponse.OK(response);
    }
}
