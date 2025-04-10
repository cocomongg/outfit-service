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
    void should_throwCoreException_when_getNonExistingCategoryId() {
        // given
        Long categoryId = 1L;

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> categoryService.getCategory(categoryId));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(CategoryErrorCode.CATEGORY_NOT_FOUND);
    }

    @DisplayName("카테고리 조회 시, 존재하는 카테고리 ID를 입력하면 해당 카테고리를 반환한다.")
    @Test
    void should_returnCategory_when_existingCategoryId() {
        // given
        Long categoryId = 1L;
        Category category = new Category("code", "name");
        when(categoryRepository.get(categoryId))
            .thenReturn(Optional.of(category));

        // when
        Category result = categoryService.getCategory(categoryId);

        // then
       assertThat(result.getCategoryCode()).isEqualTo(category.getCategoryCode());
       assertThat(result.getCategoryName()).isEqualTo(category.getCategoryName());
    }

    @DisplayName("카테고리 조회 시, 존재하지 않는 카테고리 이름을 입력하면 CoreException이 발생한다.")
    @Test
    void should_throwCoreException_when_getNonExistingCategoryName() {
        // givne
        String nonExistentCategoryName = "nonExistentCategory";

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> categoryService.getCategory(nonExistentCategoryName));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(CategoryErrorCode.CATEGORY_NOT_FOUND);
    }

    @DisplayName("카테고리 조회 시, 존재하는 카테고리 이름을 입력하면 해당 카테고리를 반환한다.")
    @Test
    void should_returnCategory_when_existingCategoryName() {
        // given
        String categoryName = "existingCategory";
        Category category = new Category("code", categoryName);
        when(categoryRepository.get(categoryName))
            .thenReturn(Optional.of(category));

        // when
        Category result = categoryService.getCategory(categoryName);

        // then
        assertThat(result.getCategoryCode()).isEqualTo(category.getCategoryCode());
        assertThat(result.getCategoryName()).isEqualTo(category.getCategoryName());
    }

}