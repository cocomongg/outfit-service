package com.musinsa.snap.outfit.domain.category.repository;

import com.musinsa.snap.outfit.domain.category.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> get(Long categoryId);

    Optional<Category> get(String categoryName);

    List<Category> getList();
}
