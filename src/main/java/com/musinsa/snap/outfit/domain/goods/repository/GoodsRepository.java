package com.musinsa.snap.outfit.domain.goods.repository;

import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import java.util.Optional;

public interface GoodsRepository {

    Optional<Goods> get(Long goodsId);

    PageResult<Goods> getList(GetGoodsListQuery query);

    boolean existsByGoodsName(String goodsName);

    Goods save(Goods goods);
}
