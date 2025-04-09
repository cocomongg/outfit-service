package com.musinsa.snap.outfit.domain.goods.model;

import com.musinsa.snap.outfit.domain.common.model.BaseEntity;
import com.musinsa.snap.outfit.domain.goods.dto.CreateGoodsCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "goods")
public class Goods extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "is_deleted")
    private boolean deleted;

    public Goods(CreateGoodsCommand command) {
        this.goodsName = command.getGoodsName();
        this.price = command.getPrice();
        this.quantity = command.getQuantity();
        this.brandId = command.getBrandId();
        this.categoryId = command.getCategoryId();
        this.deleted = false;
    }

    public void update(Long price, String goodsName, Long quantity) {
        this.goodsName = goodsName;
        this.price = price;
        this.quantity = quantity;
    }

    public void delete() {
        this.deleted = true;
    }
}
