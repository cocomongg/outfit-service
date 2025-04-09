package com.musinsa.snap.outfit.application.goods.usecase;

import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsWithBrand;
import com.musinsa.snap.outfit.domain.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class GetGoodsListUseCase {

    private final GoodsService goodsService;

    @Transactional(readOnly = true)
    public PageResult<GoodsWithBrand> execute(GetGoodsListQuery query) {
        return goodsService.getGoodsList(query);
    }
}
