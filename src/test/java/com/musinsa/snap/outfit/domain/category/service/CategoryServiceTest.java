package com.musinsa.snap.outfit.domain.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.musinsa.snap.outfit.common.error.CoreException;
import com.musinsa.snap.outfit.domain.category.error.CategoryErrorCode;
import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.category.repository.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @DisplayName("카테고리 조회 시, 존재하지 않는 카테고리 ID를 입력하면 CoreException이 발생한다.")
    @Test
    void getCategoryNotFoundTest() {
        // Given
        Long categoryId = 1L;

        // When & Then
        CoreException coreException = assertThrows(CoreException.class,
            () -> categoryService.get(categoryId));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(CategoryErrorCode.CATEGORY_NOT_FOUND);
    }

    @DisplayName("카테고리 조회 시, 존재하는 카테고리 ID를 입력하면 해당 카테고리를 반환한다.")
    @Test
    void getCategoryFoundTest() {
        // Given
        Long categoryId = 1L;
        Category category = new Category("code", "name");
        when(categoryRepository.get(categoryId))
            .thenReturn(Optional.of(category));

        // When
        Category result = categoryService.get(categoryId);

        // Then
       assertThat(result.getCategoryCode()).isEqualTo(category.getCategoryCode());
       assertThat(result.getCategoryName()).isEqualTo(category.getCategoryName());
    }

}