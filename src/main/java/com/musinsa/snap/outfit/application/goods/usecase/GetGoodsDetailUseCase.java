package com.musinsa.snap.outfit.application.goods.usecase;

import com.musinsa.snap.outfit.application.goods.dto.GoodsDetailResult;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.service.BrandService;
import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.category.service.CategoryService;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class GetGoodsDetailUseCase {

    private final GoodsService goodsService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public GoodsDetailResult execute(Long goodsId) {
        Goods goods = goodsService.getGoods(goodsId);
        Brand brand = brandService.getBrand(goods.getBrandId());
        Category category = categoryService.getCategory(goods.getCategoryId());

        return new GoodsDetailResult(goods, brand, category);
    }
}
