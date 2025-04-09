package com.musinsa.snap.outfit.domain.category.service;

import com.musinsa.snap.outfit.common.error.CoreException;
import com.musinsa.snap.outfit.domain.category.error.CategoryErrorCode;
import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category getCategory(Long categoryId) {
        return categoryRepository.get(categoryId)
            .orElseThrow(() -> new CoreException(CategoryErrorCode.CATEGORY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Category getCategory(String categoryName) {
        return categoryRepository.get(categoryName)
            .orElseThrow(() -> new CoreException(CategoryErrorCode.CATEGORY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Category> getCategoryList() {
        return categoryRepository.getList();
    }
}
