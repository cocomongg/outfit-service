package com.musinsa.snap.outfit.application.goods.dto;

import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import lombok.Getter;

@Getter

public class GoodsDetailResult {
    private final Long goodsId;
    private final String goodsName;
    private final Long price;
    private final Long brandId;
    private final String brandName;
    private final String categoryCode;
    private final String categoryName;

    public GoodsDetailResult(Goods goods, Brand brand, Category category) {
        this.goodsId = goods.getGoodsId();
        this.goodsName = goods.getGoodsName();
        this.price = goods.getPrice();
        this.brandId = brand.getBrandId();
        this.brandName = brand.getBrandName();
        this.categoryCode = category.getCategoryCode();
        this.categoryName = category.getCategoryName();
    }
}
