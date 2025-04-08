package com.musinsa.snap.outfit.interfaces.api.category.dto;

import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CategoryResponse {

    @Getter
    @RequiredArgsConstructor
    public static class CategoryInfo {

        @Schema(description = "카테고리 코드", example = "001")
        private final String categoryCode;

        @Schema(description = "카테고리 이름", example = "상의")
        private final String categoryName;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CategoryListResponse {
        @Schema(description = "카테고리 목록")
        private final List<CategoryInfo> categories;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CategoriesLowestPriceResponse {
        @Schema(description = "카테고리별 최저 가격 목록")
        private final List<CategoryLowestPriceInfo> categories;

        @Schema(description = "최저가격 총액", example = "34100")
        private final Long totalPrice;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CategoryLowestPriceInfo {
        @Schema(description = "카테고리 코드", example = "001")
        private final String categoryCode;

        @Schema(description = "카테고리 이름", example = "상의")
        private final String categoryName;

        @Schema(description = "브랜드 정보")
        private final BrandInfo brandInfo;

        @Schema(description = "상품 정보")
        private final GoodsInfo goodsInfo;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CategoryExtremePricesResponse {
        @Schema(description = "카테고리 코드", example = "001")
        private final String categoryCode;

        @Schema(description = "카테고리 이름", example = "상의")
        private final String categoryName;

        @Schema(description = "최저 가격 목록")
        private final List<CategoryExtremePriceInfo> lowestPrices;

        @Schema(description = "최고 가격 목록")
        private final List<CategoryExtremePriceInfo> highestPrices;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CategoryExtremePriceInfo {
        @Schema(description = "브랜드 정보")
        private final BrandInfo brandInfo;

        @Schema(description = "상품 정보")
        private final GoodsInfo goodsInfo;
    }
}
