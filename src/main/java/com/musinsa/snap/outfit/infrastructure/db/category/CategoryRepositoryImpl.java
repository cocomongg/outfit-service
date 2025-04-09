package com.musinsa.snap.outfit.infrastructure.db.category;

import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.category.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Optional<Category> get(Long categoryId) {
        return categoryJpaRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> get(String categoryName) {
        return categoryJpaRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Category> getList() {
        return categoryJpaRepository.findAll();
    }
}

