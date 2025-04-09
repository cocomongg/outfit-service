package com.musinsa.snap.outfit.infrastructure.brand.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.musinsa.snap.outfit.config.JPAConfig;
import com.musinsa.snap.outfit.domain.brand.dto.CreateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.dto.GetBrandListQuery;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.repository.BrandRepository;
import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.infrastructure.db.brand.repository.BrandJpaRepository;
import com.musinsa.snap.outfit.infrastructure.db.brand.repository.BrandRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


@DataJpaTest
@Import({BrandRepositoryImpl.class, JPAConfig.class})
class BrandRepositoryImplTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandJpaRepository brandJpaRepository;

    @Test
    @DisplayName("새 브랜드를 저장한다.")
    void should_saveBrand_when_newBrandName() {
        // given
        String brandName = "brandName";
        CreateBrandCommand command = new CreateBrandCommand(brandName);
        Brand brand = new Brand(command);

        // when
        Brand result = brandRepository.save(brand);

        // then
        assertThat(result.getBrandId()).isNotNull();
        Optional<Brand> optionalGetBrand = brandRepository.get(result.getBrandId());

        assertThat(optionalGetBrand).isPresent();
        Brand getBrand = optionalGetBrand.get();
        assertThat(getBrand.getBrandId()).isEqualTo(result.getBrandId());
        assertThat(getBrand.getBrandName()).isEqualTo(brandName);
    }

    @Test
    @DisplayName("삭제되지 않은 브랜드ID로 조회 시, ID에 해당하는 브랜드를 반환한다")
    void should_returnBrand_when_notDeletedBrandId() {
        // given
        String brandName = "brandName";
        CreateBrandCommand command = new CreateBrandCommand(brandName);
        Brand brand = brandJpaRepository.save(new Brand(command));

        // when
        Optional<Brand> optionalResult = brandRepository.get(brand.getBrandId());

        // then
        assertThat(optionalResult).isPresent();
        Brand result = optionalResult.get();
        assertThat(result.getBrandId()).isEqualTo(brand.getBrandId());
        assertThat(result.getBrandName()).isEqualTo(brandName);
    }

    @Test
    @DisplayName("삭제된 브랜드는 ID로 조회 시, 조회되지 않는다.")
    void should_notReturnBrand_when_deletedBrandId() {
        // given
        String brandName = "brandName";
        CreateBrandCommand command = new CreateBrandCommand(brandName);
        Brand brand = new Brand(command);
        brand.delete();
        brandJpaRepository.save(brand);

        // when
        Optional<Brand> optionalResult = brandRepository.get(brand.getBrandId());

        // then
        assertThat(optionalResult).isNotPresent();
    }

    @Test
    @DisplayName("브랜드 목록 조회 시, 삭제되지 않은 브랜드만 조회된다.")
    void should_returnPagedBrands_when_multipleBrands() {
        // given
        List<Brand> existsBrandList = new ArrayList<>();
        List<Brand> deletedBrandList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            CreateBrandCommand command = new CreateBrandCommand("brandName" + i);
            existsBrandList.add(new Brand(command));
        }
        for(int i = 0; i < 5; i++) {
            CreateBrandCommand command = new CreateBrandCommand("deleteBrandName" + i);
            Brand brand = new Brand(command);
            brand.delete();
            deletedBrandList.add(brand);
        }

        brandJpaRepository.saveAll(existsBrandList);
        brandJpaRepository.saveAll(deletedBrandList);

        // when: 페이지 0, 크기 10
        GetBrandListQuery query = new GetBrandListQuery(0, 10);
        PageResult<Brand> result = brandRepository.getList(query);

        // then
        assertThat(result.getTotalElements()).isEqualTo(existsBrandList.size());
        assertThat(result.getTotalPages()).isEqualTo(existsBrandList.size() / query.getPageSize() + 1);
        assertThat(result.getPageNo()).isEqualTo(query.getPageNo());
        assertThat(result.getPageSize()).isEqualTo(query.getPageSize());

        List<Brand> resultContent = result.getContent();
        for (Brand brand : resultContent) {
            assertThat(brand.isDeleted()).isFalse();
        }
    }

    @Test
    @DisplayName("브랜드 목록 조회 시, 최근 생성된 순으로 정렬된다.")
    void should_returnSortedCreatedAtDesc_when_multipleBrands() {
        // given
        CreateBrandCommand command1 = new CreateBrandCommand("brandName1");
        CreateBrandCommand command2 = new CreateBrandCommand("brandName2");
        CreateBrandCommand command3 = new CreateBrandCommand("brandName3");
        Brand brand1 = brandJpaRepository.save(new Brand(command1));
        Brand brand2 = brandJpaRepository.save(new Brand(command2));
        Brand brand3 = brandJpaRepository.save(new Brand(command3));

        // when
        GetBrandListQuery query = new GetBrandListQuery(0, 10);
        PageResult<Brand> result = brandRepository.getList(query);

        // then: 가장 최근 생성된 B3, B2, B1 순으로 반환
        List<Brand> resultContent = result.getContent();
        assertThat(resultContent).hasSize(3);
        assertThat(resultContent.get(0).getBrandId()).isEqualTo(brand3.getBrandId());
        assertThat(resultContent.get(1).getBrandId()).isEqualTo(brand2.getBrandId());
        assertThat(resultContent.get(2).getBrandId()).isEqualTo(brand1.getBrandId());
    }

    @Test
    @DisplayName("브랜드 이름 존재 여부 조회 시, 존재하면 true를 반환한다.")
    void should_returnTrue_when_existsByBrandName() {
        // given
        String brandName = "brandName";
        CreateBrandCommand command = new CreateBrandCommand(brandName);
        brandRepository.save(new Brand(command));

        // when
        boolean result = brandRepository.existsByBrandName(brandName);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("브랜드 이름 존재 여부 조회 시, 존재하지 않으면 false를 반환한다.")
    void should_returnFalse_when_notExistsByBrandName() {
        // given
        String nonExistentBrandName = "nonExistentBrandName";

        // when
        boolean result = brandRepository.existsByBrandName(nonExistentBrandName);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("브랜드 이름 존재 여부 조회 시, 존재하는 브랜드 이름이 삭제된 경우 false를 반환한다.")
    void should_returnFalse_when_existsByBrandNameButDeleted() {
        // given
        String brandName = "brandName";
        CreateBrandCommand command = new CreateBrandCommand(brandName);
        Brand brand = new Brand(command);
        brand.delete();
        brandJpaRepository.save(brand);

        // when
        boolean result = brandRepository.existsByBrandName(brandName);

        // then
        assertThat(result).isFalse();
    }
}