package com.musinsa.snap.outfit.application.goods.usecase;

import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.service.BrandService;
import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.category.service.CategoryService;
import com.musinsa.snap.outfit.domain.goods.dto.CreateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateGoodsUseCase {

    private final GoodsService goodsService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public Goods execute(CreateGoodsCommand command) {
        Brand brand = brandService.getBrand(command.getBrandId());
        Category category = categoryService.getCategory(command.getCategoryId());

        return goodsService.createGoods(command);
    }
}
