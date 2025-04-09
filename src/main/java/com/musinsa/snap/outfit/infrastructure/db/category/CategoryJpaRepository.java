package com.musinsa.snap.outfit.infrastructure.db.category;

import com.musinsa.snap.outfit.domain.category.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);
}
