package com.musinsa.snap.outfit.application.category.dto;

import com.musinsa.snap.outfit.domain.goods.dto.GoodsPriceInfo;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetCategoryExtremePriceResult {
    private final Long categoryId;
    private final String categoryName;
    private final List<GoodsPriceInfo> goodsLowestPriceInfo;
    private final List<GoodsPriceInfo> goodsHighestPriceInfo;
}
