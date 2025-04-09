package com.musinsa.snap.outfit.infrastructure.db.category;

import com.musinsa.snap.outfit.domain.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}
