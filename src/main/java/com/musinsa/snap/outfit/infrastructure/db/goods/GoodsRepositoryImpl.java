package com.musinsa.snap.outfit.infrastructure.db.goods;

import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsPriceInfo;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsWithBrand;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.repository.GoodsRepository;
import com.musinsa.snap.outfit.infrastructure.db.goods.dto.GoodsListItemProjection;
import com.musinsa.snap.outfit.infrastructure.db.goods.dto.GoodsPriceInfoProjection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public PageResult<GoodsWithBrand> getList(GetGoodsListQuery query) {
        Page<GoodsListItemProjection> pageGoods = goodsQueryRepository.pageGoods(query);

        List<GoodsListItemProjection> content = pageGoods.getContent();
        List<GoodsWithBrand> goodsList = content.stream().map(goodsListItem ->
                GoodsWithBrand.builder()
                    .goodsId(goodsListItem.getGoodsId())
                    .goodsName(goodsListItem.getGoodsName())
                    .price(goodsListItem.getPrice())
                    .quantity(goodsListItem.getQuantity())
                    .brandId(goodsListItem.getBrandId())
                    .brandName(goodsListItem.getBrandName())
                    .categoryId(goodsListItem.getCategoryId())
                    .deleted(goodsListItem.isDeleted())
                    .build())
            .toList();

        return new PageResult<>(
                pageGoods.getNumber(),
                pageGoods.getSize(),
                pageGoods.getTotalElements(),
                pageGoods.getTotalPages(),
                goodsList
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

    @Override
    public List<GoodsPriceInfo> getExtremePriceInfoByCategory(Long categoryId, boolean isLowest) {
        List<GoodsPriceInfoProjection> projectionList = goodsQueryRepository.getGoodsPriceInfoByCategory(
            categoryId, isLowest);

        return projectionList.stream()
            .map(goodsPriceInfoProjection ->
                GoodsPriceInfo.builder()
                    .goodsId(goodsPriceInfoProjection.getGoodsId())
                    .goodsName(goodsPriceInfoProjection.getGoodsName())
                    .price(goodsPriceInfoProjection.getPrice())
                    .brandId(goodsPriceInfoProjection.getBrandId())
                    .brandName(goodsPriceInfoProjection.getBrandName())
                    .categoryId(goodsPriceInfoProjection.getCategoryId())
                    .categoryName(goodsPriceInfoProjection.getCategoryName())
                    .build()
            ).collect(Collectors.toList());
    }
}
