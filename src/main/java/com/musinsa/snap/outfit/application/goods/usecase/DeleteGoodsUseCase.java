package com.musinsa.snap.outfit.application.goods.usecase;

import com.musinsa.snap.outfit.domain.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteGoodsUseCase {

    private final GoodsService goodsService;

    public void execute(Long goodsId) {
        goodsService.deleteGoods(goodsId);
    }
}
