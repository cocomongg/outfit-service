package com.musinsa.snap.outfit.domain.goods.service;

import com.musinsa.snap.outfit.common.error.CoreException;
import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.CreateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsWithBrand;
import com.musinsa.snap.outfit.domain.goods.dto.UpdateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.error.GoodsErrorCode;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class GoodsService {

    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public Goods getGoods(Long goodsId) {
        return goodsRepository.get(goodsId)
            .orElseThrow(() -> new CoreException(GoodsErrorCode.GOODS_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public PageResult<GoodsWithBrand> getGoodsList(GetGoodsListQuery query) {
        return goodsRepository.getList(query);
    }

    @Transactional
    public Goods createGoods(CreateGoodsCommand command) {
        this.validateGoodsNameNotExists(command.getGoodsName());
        Goods goods = new Goods(command);
        return goodsRepository.save(goods);
    }

    @Transactional
    public Goods updateGoods(UpdateGoodsCommand command) {
        Goods goods = this.getGoods(command.getGoodsId());

        String newName = command.getGoodsName();
        if (!goods.getGoodsName().equals(newName)) {
            validateGoodsNameNotExists(newName);
        }

        goods.update(command.getPrice(), command.getGoodsName(), command.getQuantity());
        return goods;
    }

    @Transactional
    public void deleteGoods(Long goodsId) {
        Goods goods = this.getGoods(goodsId);
        goods.delete();
    }

    @Transactional(readOnly = true)
    public void validateGoodsNameNotExists(String goodsName) {
        if (goodsRepository.existsByGoodsName(goodsName)) {
            throw new CoreException(GoodsErrorCode.GOODS_NAME_ALREADY_EXISTS);
        }
    }
}
