package com.musinsa.snap.outfit.interfaces.api.brand.dto;

import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class BrandResponse {

    @Getter
    @RequiredArgsConstructor
    public static class BrandCreateResponse {
        @Schema(description = "생성된 브랜드 ID", example = "1")
        private final Long brandId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class BrandDetailResponse {
        @Schema(description = "브랜드 ID", example = "1")
        private final Long brandId;

        @Schema(description = "브랜드명", example = "A")
        private final String brandName;

        @Schema(description = "생성일시", example = "2025-04-05T12:00:00Z")
        private final LocalDateTime createdAt;
    }

    @Getter
    @RequiredArgsConstructor
    public static class BrandListResponse {
        @Schema(description = "페이지 번호", example = "0")
        private final int pageNo;

        @Schema(description = "페이지 크기", example = "10")
        private final int pageSize;

        @Schema(description = "전체 항목 수", example = "60")
        private final int totalElements;

        @Schema(description = "전체 페이지 수", example = "3")
        private final int totalPages;

        @Schema(description = "브랜드 목록")
        private final List<BrandInfo> brands;
    }

    @Getter
    @AllArgsConstructor
    public static class BrandInfo {
        @Schema(description = "브랜드 ID", example = "1")
        private final Long brandId;

        @Schema(description = "브랜드명", example = "A")
        private final String brandName;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SingleBrandLowestPriceResponse {
        private final Long brandId;
        private final String brandName;
        private final List<CategoryWithGoodsInfo> categories;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CategoryWithGoodsInfo {
        private final Long categoryId;
        private final String categoryName;
        private final List<GoodsInfo> goodsList;
    }
}
