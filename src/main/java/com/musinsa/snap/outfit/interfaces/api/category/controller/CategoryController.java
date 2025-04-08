package com.musinsa.snap.outfit.interfaces.api.category.controller;

import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoriesLowestPriceResponse;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryExtremePriceInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryExtremePricesResponse;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryListResponse;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryLowestPriceInfo;
import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController implements CategoryControllerDocs{

    @Override
    @GetMapping
    public ApiSuccessResponse<CategoryListResponse> getCategoryList() {
        List<CategoryInfo> categories = new ArrayList<>();
        categories.add(new CategoryInfo("001", "상의"));
        categories.add(new CategoryInfo("002", "하의"));
        CategoryListResponse response = new CategoryListResponse(categories);
        return ApiSuccessResponse.OK(response);
    }

    @Override
    @GetMapping("/lowest-price")
    public ApiSuccessResponse<CategoriesLowestPriceResponse> getCategoriesLowestPrice() {
        List<CategoryLowestPriceInfo> lowestPriceInfos = new ArrayList<>();
        GoodsInfo goodsInfo1 = new GoodsInfo(1L, "상품A", 10000L);
        GoodsInfo goodsInfo2 = new GoodsInfo(2L, "상품B", 15000L);
        BrandInfo brandInfo = new BrandInfo(1L, "A");

        lowestPriceInfos.add(new CategoryLowestPriceInfo("001", "상의", brandInfo, goodsInfo1));
        lowestPriceInfos.add(new CategoryLowestPriceInfo("002", "하의", brandInfo, goodsInfo2));

        CategoriesLowestPriceResponse response = new CategoriesLowestPriceResponse(lowestPriceInfos, 25000L);
        return ApiSuccessResponse.OK(response);
    }

    @Override
    @GetMapping("/extreme-prices")
    public ApiSuccessResponse<CategoryExtremePricesResponse> getCategoryLowestAndHighestPrices(
        @RequestParam String categoryName) {
        List<CategoryExtremePriceInfo> lowestPrices = new ArrayList<>();
        GoodsInfo lowGoods = new GoodsInfo(1L, "최저상품", 8000L);
        BrandInfo lowBrand = new BrandInfo(1L, "A");
        lowestPrices.add(new CategoryExtremePriceInfo(lowBrand, lowGoods));

        // 임의의 최고 가격 정보
        List<CategoryExtremePriceInfo> highestPrices = new ArrayList<>();
        GoodsInfo highGoods = new GoodsInfo(2L, "최고상품", 20000L);
        BrandInfo highBrand = new BrandInfo(2L, "B");
        highestPrices.add(new CategoryExtremePriceInfo(highBrand, highGoods));

        CategoryExtremePricesResponse response = new CategoryExtremePricesResponse(
            "001", categoryName, lowestPrices, highestPrices);
        return ApiSuccessResponse.OK(response);
    }
}
