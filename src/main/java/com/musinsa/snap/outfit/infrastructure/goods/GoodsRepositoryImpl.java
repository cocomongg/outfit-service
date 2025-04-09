package com.musinsa.snap.outfit.infrastructure.goods;

import com.musinsa.snap.outfit.domain.common.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.repository.GoodsRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepository {

    private final GoodsJpaRepository goodsJpaRepository;
    private final GoodsQueryRepository goodsQueryRepository;

    @Override
    public Optional<Goods> get(Long goodsId) {
        return goodsJpaRepository.findByGoodsIdAndDeletedIsFalse(goodsId);
    }

    @Override
    public PageResult<Goods> getList(GetGoodsListQuery query) {
        Page<Goods> pageGoods = goodsQueryRepository.pageGoods(query);

        return new PageResult<>(
                pageGoods.getNumber(),
                pageGoods.getSize(),
                pageGoods.getTotalElements(),
                pageGoods.getTotalPages(),
                pageGoods.getContent()
        );
    }

    @Override
    public boolean existsByGoodsName(String goodsName) {
        return goodsJpaRepository.existsByGoodsNameAndDeletedIsFalse(goodsName);
    }

    @Override
    public Goods save(Goods goods) {
        return goodsJpaRepository.save(goods);
    }
}
