package com.musinsa.snap.outfit.application.category.usecase;

import com.musinsa.snap.outfit.application.category.dto.GetCategoryExtremePriceResult;
import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.category.service.CategoryService;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsPriceInfo;
import com.musinsa.snap.outfit.domain.goods.service.GoodsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class GetCategoryExtremePricesUseCase {

    private final CategoryService categoryService;
    private final GoodsService goodsService;

    @Transactional(readOnly = true)
    public GetCategoryExtremePriceResult execute(String categoryName) {
        Category category = categoryService.getCategory(categoryName);

        List<GoodsPriceInfo> goodsLowestPriceInfoList = goodsService.getExtremePriceInfoByCategory(
            category.getCategoryId(), true);
        List<GoodsPriceInfo> goodsHighestPriceInfoList = goodsService.getExtremePriceInfoByCategory(
            category.getCategoryId(), false);

        return new GetCategoryExtremePriceResult(
            category.getCategoryId(),
            category.getCategoryName(),
            goodsLowestPriceInfoList,
            goodsHighestPriceInfoList
        );
    }
}
