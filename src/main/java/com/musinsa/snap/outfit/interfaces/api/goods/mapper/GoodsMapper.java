package com.musinsa.snap.outfit.interfaces.api.goods.mapper;

import com.musinsa.snap.outfit.application.goods.dto.GoodsDetailResult;
import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.CreateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsWithBrand;
import com.musinsa.snap.outfit.domain.goods.dto.UpdateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryInfo;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GetGoodsListRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsCreateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsUpdateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsListItem;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsListResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper {

    public CreateGoodsCommand toCreateGoodsCommand(GoodsCreateRequest request) {
        return CreateGoodsCommand.builder()
            .goodsName(request.getGoodsName())
            .price(request.getPrice())
            .quantity(request.getQuantity())
            .brandId(request.getBrandId())
            .categoryId(request.getCategoryId())
            .build();
    }

    public GetGoodsListQuery toGetGoodsListQuery(GetGoodsListRequest request) {
        return new GetGoodsListQuery(request.getPageNo(), request.getPageSize(),
            request.getBrandId());
    }

    public UpdateGoodsCommand toUpdateGoodsCommand(Long goodsId, GoodsUpdateRequest request) {
        return UpdateGoodsCommand.builder()
            .goodsId(goodsId)
            .goodsName(request.getGoodsName())
            .price(request.getPrice())
            .quantity(request.getQuantity())
            .build();
    }

    public GoodsCreateResponse toCreateGoodsResponse(Goods goods) {
        return new GoodsCreateResponse(goods.getGoodsId());
    }

    public GoodsListResponse toGoodsListResponse(PageResult<GoodsWithBrand> goodsPage) {
        List<GoodsListItem> goodsList = new ArrayList<>();
        for (GoodsWithBrand goods : goodsPage.getContent()) {
            BrandInfo brandInfo = new BrandInfo(goods.getBrandId(), goods.getBrandName());
            GoodsListItem item = GoodsListItem.builder()
                .goodsId(goods.getGoodsId())
                .goodsName(goods.getGoodsName())
                .price(goods.getPrice())
                .brand(brandInfo)
                .build();

            goodsList.add(item);
        }

        return GoodsListResponse.builder()
            .pageNo(goodsPage.getPageNo())
            .pageSize(goodsPage.getPageSize())
            .totalElements(goodsPage.getTotalElements())
            .totalPages(goodsPage.getTotalPages())
            .goods(goodsList)
            .build();
    }

    public GoodsDetailResponse toGoodsDetailResponse(GoodsDetailResult goodsDetailResult) {
        BrandInfo brandInfo = new BrandInfo(goodsDetailResult.getBrandId(),
            goodsDetailResult.getBrandName());

        CategoryInfo categoryInfo = new CategoryInfo(goodsDetailResult.getCategoryCode(),
            goodsDetailResult.getCategoryName());

        return GoodsDetailResponse.builder()
            .goodsId(goodsDetailResult.getGoodsId())
            .goodsName(goodsDetailResult.getGoodsName())
            .price(goodsDetailResult.getPrice())
            .brand(brandInfo)
            .category(categoryInfo)
            .build();
    }
}
