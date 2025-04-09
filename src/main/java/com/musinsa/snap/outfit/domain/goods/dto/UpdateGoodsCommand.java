package com.musinsa.snap.outfit.domain.goods.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateGoodsCommand {
    private final Long goodsId;
    private final String goodsName;
    private final Long price;
    private final Long quantity;
}
