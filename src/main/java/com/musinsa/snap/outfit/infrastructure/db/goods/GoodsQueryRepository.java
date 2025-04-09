package com.musinsa.snap.outfit.infrastructure.db.goods;

import static com.musinsa.snap.outfit.domain.goods.model.QGoods.goods;

import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    public Page<Goods> pageGoods(GetGoodsListQuery query) {
        PageRequest pageable = PageRequest.of(query.getPageNo(), query.getPageSize());
        JPAQuery<?> commonListQuery = this.generateGoodsListQuery(query);

        JPAQuery<Long> countQuery = commonListQuery.clone()
            .select(goods.count());

        List<Goods> content = commonListQuery.clone()
            .select(goods)
            .orderBy(goods.createdAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> {
            Long count = countQuery.fetchOne();
            return count != null ? count : 0;
        });
    }

    private JPAQuery<?> generateGoodsListQuery(GetGoodsListQuery query) {
        return queryFactory.from(goods)
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
}
