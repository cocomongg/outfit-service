package com.musinsa.snap.outfit.domain.goods.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GoodsWithBrand {
    private final Long goodsId;
    private final String goodsName;
    private final Long price;
    private final Long quantity;
    private final Long brandId;
    private final String brandName;
    private final Long categoryId;
    private final boolean deleted;
}
