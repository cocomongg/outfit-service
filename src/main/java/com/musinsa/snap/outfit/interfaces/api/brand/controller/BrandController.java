package com.musinsa.snap.outfit.interfaces.api.brand.controller;

import static com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.CategoryWithGoodsInfo;

import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.CreateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.UpdateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandListResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.SingleBrandLowestPriceResponse;
import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/brands")
@RestController
public class BrandController implements BrandControllerDocs{

    @Override
    @PostMapping
    public ApiSuccessResponse<BrandCreateResponse> createBrand(@RequestBody CreateBrandRequest request) {
        BrandCreateResponse response = new BrandCreateResponse(10L);
        return ApiSuccessResponse.CREATED(response);
    }

    @Override
    @GetMapping
    public ApiSuccessResponse<BrandListResponse> getBrandList(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize) {
        // Mock 데이터 생성
        List<BrandInfo> brands = new ArrayList<>();
        brands.add(new BrandInfo(1L, "A"));
        brands.add(new BrandInfo(2L, "B"));
        brands.add(new BrandInfo(3L, "C"));

        BrandListResponse response = new BrandListResponse(
            pageNo,
            pageSize,
            60,
            3,
            brands
        );

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @GetMapping("/{brandId}")
    public ApiSuccessResponse<BrandDetailResponse> getBrandDetail(@PathVariable Long brandId) {
        BrandDetailResponse response = new BrandDetailResponse(
            brandId,
            "브랜드 " + brandId,
            LocalDateTime.now()
        );

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @PutMapping("/{brandId}")
    public ApiSuccessResponse<BrandDetailResponse> updateBrand(
        @PathVariable Long brandId, @RequestBody UpdateBrandRequest request) {

        BrandDetailResponse response = new BrandDetailResponse(
            brandId,
            request.getBrandName(),
            LocalDateTime.now()
        );

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @DeleteMapping("/{brandId}")
    public ApiSuccessResponse<?> deleteBrand(@PathVariable Long brandId) {
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
