package com.musinsa.snap.outfit.interfaces.api.category.mapper;

import com.musinsa.snap.outfit.application.category.dto.GetCategoryExtremePriceResult;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsPriceInfo;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryExtremePriceInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryExtremePricesResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsInfo;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryExtremePricesResponse toCategoryExtremePricesResponse(
        GetCategoryExtremePriceResult result) {

        List<GoodsPriceInfo> goodsHighestPriceInfo = result.getGoodsHighestPriceInfo();
        List<GoodsPriceInfo> goodsLowestPriceInfo = result.getGoodsLowestPriceInfo();

        List<CategoryExtremePriceInfo> highestPrices= goodsHighestPriceInfo.stream()
            .map(info -> {
                BrandInfo brandInfo = new BrandInfo(info.getBrandId(), info.getBrandName());
                GoodsInfo goodsInfo = new GoodsInfo(info.getGoodsId(), info.getGoodsName(),
                    info.getPrice());
                return new CategoryExtremePriceInfo(
                    brandInfo,
                    goodsInfo
                );
            })
            .toList();

        List<CategoryExtremePriceInfo> lowestPrices = goodsLowestPriceInfo.stream()
            .map(info -> {
                BrandInfo brandInfo = new BrandInfo(info.getBrandId(), info.getBrandName());
                GoodsInfo goodsInfo = new GoodsInfo(info.getGoodsId(), info.getGoodsName(),
                    info.getPrice());
                return new CategoryExtremePriceInfo(
                    brandInfo,
                    goodsInfo
                );
            })
            .toList();

        return new CategoryExtremePricesResponse(
            result.getCategoryId(),
            result.getCategoryName(),
            lowestPrices,
            highestPrices
        );
    }
}
