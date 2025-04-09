package com.musinsa.snap.outfit.infrastructure.db.goods;

import com.musinsa.snap.outfit.domain.goods.model.Goods;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsJpaRepository extends JpaRepository<Goods, Long> {
    Optional<Goods> findByGoodsIdAndDeletedIsFalse(Long id);
    boolean existsByGoodsNameAndDeletedIsFalse(String goodsName);
}
