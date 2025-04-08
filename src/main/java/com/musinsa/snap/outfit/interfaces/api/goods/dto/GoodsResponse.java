package com.musinsa.snap.outfit.interfaces.api.goods.dto;

import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryInfo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class GoodsResponse {

    @Getter
    @RequiredArgsConstructor
    public static class GoodsCreateResponse {
        private final Long goodsId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class GoodsListResponse {
        private final Integer pageNo;
        private final Integer pageSize;
        private final Long totalElements;
        private final Integer totalPages;
        private final List<GoodsInfo> goods;
    }

    @Getter
    @RequiredArgsConstructor
    public static class GoodsInfo {
        private final Long goodsId;
        private final String goodsName;
        private final Long price;
    }

    @Getter
    public static class GoodsDetailResponse extends GoodsInfo{
        private final BrandInfo brand;
        private final CategoryInfo category;

        public GoodsDetailResponse(Long goodsId, String goodsName, Long price,
            LocalDateTime createdAt, BrandInfo brandInfo, CategoryInfo categoryInfo) {
            super(goodsId, goodsName, price);
            this.brand = brandInfo;
            this.category = categoryInfo;
        }
    }
}
