package com.musinsa.snap.outfit.domain.goods.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateGoodsCommand {
    private final String goodsName;
    private final Long price;
    private final Long quantity;
    private final Long brandId;
    private final Long categoryId;
}
