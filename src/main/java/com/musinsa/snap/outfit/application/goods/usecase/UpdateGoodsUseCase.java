package com.musinsa.snap.outfit.application.goods.usecase;

import com.musinsa.snap.outfit.domain.goods.dto.UpdateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UpdateGoodsUseCase {

    private final GoodsService goodsService;

    @Transactional
    public Goods execute(UpdateGoodsCommand command) {
        return goodsService.updateGoods(command);
    }
}
