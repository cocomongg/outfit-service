package com.musinsa.snap.outfit.infrastructure.db.goods.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoodsPriceInfoProjection {
    private final Long goodsId;
    private final String goodsName;
    private final Long price;
    private final Long brandId;
    private final String brandName;
    private final Long categoryId;
    private final String categoryName;
}
