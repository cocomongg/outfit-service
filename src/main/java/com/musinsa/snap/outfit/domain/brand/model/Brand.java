package com.musinsa.snap.outfit.domain.brand.model;

import com.musinsa.snap.outfit.domain.common.BaseEntity;
import com.musinsa.snap.outfit.domain.brand.dto.CreateBrandCommand;
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
@Table(name = "brands")
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "is_deleted")
    private boolean deleted;

    public Brand(CreateBrandCommand command) {
        this.brandName = command.getBrandName();
    }

    public void update(String brandName) {
        this.brandName = brandName;
    }

    public void delete() {
        this.deleted = true;
    }
}