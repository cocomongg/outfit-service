package com.musinsa.snap.outfit.infrastructure.db.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.musinsa.snap.outfit.config.JPAConfig;
import com.musinsa.snap.outfit.domain.category.model.Category;
import com.musinsa.snap.outfit.domain.category.repository.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({CategoryRepositoryImpl.class, JPAConfig.class})
class CategoryRepositoryImplTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @DisplayName("존재하는 카테고리 ID로 조회 시, 해당 카테고리를 반환한다.")
    @Test
    void should_returnCategory_when_categoryIdExists() {
        // given
        Category category = new Category("code", "name");
        Category savedCategory = categoryJpaRepository.save(category);

        // when
        Optional<Category> result = categoryRepository.get(category.getCategoryId());

        // then
        assertThat(result.isPresent()).isTrue();
        Category resultCategory = result.get();
        assertThat(resultCategory.getCategoryId()).isEqualTo(savedCategory.getCategoryId());
        assertThat(resultCategory.getCategoryCode()).isEqualTo(savedCategory.getCategoryCode());
        assertThat(resultCategory.getCategoryName()).isEqualTo(savedCategory.getCategoryName());
    }

    @DisplayName("존재하지 않는 카테고리 ID로 조회 시, 빈 Optional을 반환한다.")
    @Test
    void should_returnEmptyOptional_when_categoryIdNotExists() {
        // given
        Long nonExistentCategoryId = 999L;

        // when
        Optional<Category> result = categoryRepository.get(nonExistentCategoryId);

        // then
        assertThat(result.isPresent()).isFalse();
    }
}