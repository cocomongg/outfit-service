package com.musinsa.snap.outfit.infrastructure.db.goods;

import static com.musinsa.snap.outfit.domain.brand.model.QBrand.brand;
import static com.musinsa.snap.outfit.domain.category.model.QCategory.category;
import static com.musinsa.snap.outfit.domain.goods.model.QGoods.goods;

import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.infrastructure.db.goods.dto.GoodsListItemProjection;
import com.musinsa.snap.outfit.infrastructure.db.goods.dto.GoodsPriceInfoProjection;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GoodsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<GoodsListItemProjection> pageGoods(GetGoodsListQuery query) {
        PageRequest pageable = PageRequest.of(query.getPageNo(), query.getPageSize());
        JPAQuery<?> commonListQuery = this.generateGoodsListQuery(query);

        JPAQuery<Long> countQuery = commonListQuery.clone()
            .select(goods.count());

        List<GoodsListItemProjection> content = commonListQuery.clone()
            .select(Projections.constructor(
                GoodsListItemProjection.class,
                goods.goodsId,
                goods.goodsName,
                goods.price,
                goods.quantity,
                brand.brandId,
                brand.brandName,
                goods.categoryId,
                goods.deleted
            ))
            .orderBy(goods.createdAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> {
            Long count = countQuery.fetchOne();
            return count != null ? count : 0;
        });
    }

    public List<GoodsPriceInfoProjection> getGoodsPriceInfoByCategory(Long categoryId, boolean isLowest) {
        return queryFactory.select(Projections.constructor(
            GoodsPriceInfoProjection.class,
                goods.goodsId,
                goods.goodsName,
                goods.price,
                brand.brandId,
                brand.brandName,
                category.categoryId,
                category.categoryName
            ))
            .from(goods)
            .join(brand).on(goods.brandId.eq(brand.brandId))
            .join(category).on(goods.categoryId.eq(category.categoryId))
            .where(
                this.categoryIdEq(categoryId),
                this.deletedIsFalse(),
                this.priceEq(categoryId, isLowest)
            )
            .fetch();
    }

    private Predicate priceEq(Long categoryId, boolean isLowest) {
        return isLowest ? goods.price.eq(
            JPAExpressions.select(goods.price.min())
                .from(goods)
                .where(
                    this.categoryIdEq(categoryId),
                    this.deletedIsFalse()
                )
        ) : goods.price.eq(
            JPAExpressions.select(goods.price.max())
                .from(goods)
                .where(
                    this.categoryIdEq(categoryId),
                    this.deletedIsFalse()
                )
        );
    }

    private JPAQuery<?> generateGoodsListQuery(GetGoodsListQuery query) {
        return queryFactory.from(goods)
            .join(brand).on(goods.brandId.eq(brand.brandId))
            .where(
                this.brandIdEq(query.getBrandId()),
                this.deletedIsFalse()
            );
    }

    private BooleanExpression brandIdEq(Long brandId) {
        return brandId != null ? goods.brandId.eq(brandId) : null;
    }

    private BooleanExpression deletedIsFalse() {
        return goods.deleted.isFalse();
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? goods.categoryId.eq(categoryId) : null;
    }
}
