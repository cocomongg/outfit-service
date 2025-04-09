package com.musinsa.snap.outfit.domain.goods.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetGoodsListQuery {
    private final int pageNo;
    private final int pageSize;
    private final Long brandId;
}
