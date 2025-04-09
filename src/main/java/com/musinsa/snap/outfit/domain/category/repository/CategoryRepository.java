package com.musinsa.snap.outfit.domain.category.repository;

import com.musinsa.snap.outfit.domain.category.model.Category;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> get(Long categoryId);
}
