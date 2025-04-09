package com.musinsa.snap.outfit.domain.brand.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.musinsa.snap.outfit.common.error.CoreException;
import com.musinsa.snap.outfit.domain.brand.dto.CreateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.dto.UpdateBrandCommand;
import com.musinsa.snap.outfit.domain.brand.error.BrandErrorCode;
import com.musinsa.snap.outfit.domain.brand.model.Brand;
import com.musinsa.snap.outfit.domain.brand.repository.BrandRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 생성 시, 이미 존재하는 브랜드명이 아니면 저장하고 반환한다.")
    void should_SaveAndReturnBrand_when_givenCommand() {
        // given
        String brandName = "brandName";
        CreateBrandCommand command = new CreateBrandCommand(brandName);
        Brand givenBrand = new Brand(command);
        given(brandRepository.existsByBrandName(brandName)).willReturn(false);
        given(brandRepository.save(any(Brand.class))).willReturn(givenBrand);

        // when
        Brand result = brandService.createBrand(command);

        // then
        assertThat(result.getBrandId()).isEqualTo(givenBrand.getBrandId());
        assertThat(result.getBrandName()).isEqualTo(givenBrand.getBrandName());
    }

    @Test
    @DisplayName("브랜드 생성 시, 이미 존재하는 브랜드명이라면, CoreException이 발생한다.")
    void should_throwCoreException_when_CreateWithExistsBrandName() {
        // given
        String brandName = "brandName";
        CreateBrandCommand command = new CreateBrandCommand(brandName);
        given(brandRepository.existsByBrandName(brandName)).willReturn(true);

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> brandService.createBrand(command));
        assertThat(coreException.getCoreErrorCode()).isEqualTo(BrandErrorCode.BRAND_NAME_ALREADY_EXISTS);
    }

    @Test
    @DisplayName("브랜드 이름 중복 검증 시, 이미 존재하면 CoreException이 발생한다.")
    void should_throwCoreException_when_validateBrandNameIsExists() {
        // given
        String brandName = "brandName";
        given(brandRepository.existsByBrandName(brandName)).willReturn(true);

        // when & then
        CoreException coreException = assertThrows(CoreException.class,
            () -> brandService.validateBrandNameNotExists(brandName));
        assertThat(coreException.getCoreErrorCode()).isEqualTo(BrandErrorCode.BRAND_NAME_ALREADY_EXISTS);
    }

    @Test
    @DisplayName("브랜드 이름 중복 검증 시, 존재하지 않으면 예외가 발생하지 않는다.")
    void should_notThrowException_when_validateBrandNameNotExists() {
        // given
        String brandName = "brandName";
        given(brandRepository.existsByBrandName(brandName)).willReturn(false);

        // when & then
        assertDoesNotThrow(() -> brandService.validateBrandNameNotExists(brandName));
    }

    @Test
    @DisplayName("브랜드 수정 시, 존재하지 않는 ID면 예외가 발생한다.")
    void should_throwCoreException_when_updateNonExistingBrand() {
        // given
        UpdateBrandCommand command = new UpdateBrandCommand(1L, "brandName");
        given(brandRepository.get(command.getBrandId())).willReturn(Optional.empty());

        // when & then
        CoreException coreException = assertThrows(CoreException.class,
            () -> brandService.updateBrand(command));
        assertThat(coreException.getCoreErrorCode()).isEqualTo(BrandErrorCode.BRAND_NOT_FOUND);
    }

    @Test
    @DisplayName("브랜드 수정 시, 기존 이름과 같은 입력이라면 이름 중복 검사를 하지 않는다.")
    void should_updateBrand_when_nameUnchanged() {
        // given
        String brandName = "brandName";
        Brand existingBrand = new Brand(new CreateBrandCommand(brandName));
        given(brandRepository.get(1L)).willReturn(Optional.of(existingBrand));
        UpdateBrandCommand command = new UpdateBrandCommand(1L, brandName);

        // when
        Brand result = brandService.updateBrand(command);

        // then
        assertThat(result.getBrandId()).isEqualTo(existingBrand.getBrandId());
        assertThat(result.getBrandName()).isEqualTo(existingBrand.getBrandName());
        verify(brandRepository, never()).existsByBrandName(anyString());
    }

    @Test
    @DisplayName("브랜드 수정 시, 새로운 이름이 이미 존재하면 예외가 발생한다.")
    void should_throwCoreException_when_updateDuplicateBrandName() {
        // given
        String newBrandName = "newBrandName";
        Brand existingBrand = new Brand(new CreateBrandCommand("brandName"));
        given(brandRepository.get(1L)).willReturn(Optional.of(existingBrand));
        UpdateBrandCommand command = new UpdateBrandCommand(1L, newBrandName);
        given(brandRepository.existsByBrandName(newBrandName)).willReturn(true);

        // when & then
        CoreException coreException = assertThrows(CoreException.class,
            () -> brandService.updateBrand(command));
        assertThat(coreException.getCoreErrorCode()).isEqualTo(BrandErrorCode.BRAND_NAME_ALREADY_EXISTS);
    }

    @Test
    @DisplayName("브랜드 수정 시, 새로운 이름이 존재하지 않는다면 업데이트한다.")
    void should_updateBrand_when_validNewName() {
        // given
        String newBrandName = "newBrandName";
        Brand existingBrand = new Brand(new CreateBrandCommand("OldName"));
        given(brandRepository.get(1L)).willReturn(Optional.of(existingBrand));
        UpdateBrandCommand command = new UpdateBrandCommand(1L, newBrandName);
        given(brandRepository.existsByBrandName(newBrandName)).willReturn(false);

        // when
        Brand result = brandService.updateBrand(command);

        // then
        assertThat(result.getBrandName()).isEqualTo(newBrandName);
    }

    @DisplayName("브랜드 삭제 시, 존재하지 않는 ID면 예외가 발생한다.")
    @Test
    void should_throwCoreException_when_deleteNonExistingBrand() {
        // given
        Long brandId = 1L;
        given(brandRepository.get(brandId)).willReturn(Optional.empty());

        // when & then
        CoreException coreException = assertThrows(CoreException.class,
            () -> brandService.deleteBrand(brandId));
        assertThat(coreException.getCoreErrorCode()).isEqualTo(BrandErrorCode.BRAND_NOT_FOUND);
    }

    @DisplayName("브랜드 삭제 시, 존재하는 ID면 삭제된다.")
    @Test
    void should_deleteBrand_when_existingBrandId() {
        // given
        Long brandId = 1L;
        Brand existingBrand = new Brand(new CreateBrandCommand("brandName"));
        given(brandRepository.get(brandId)).willReturn(Optional.of(existingBrand));

        // when
        brandService.deleteBrand(brandId);

        // then
        assertThat(existingBrand.isDeleted()).isTrue();
    }

    @DisplayName("브랜드 조회 시, 존재하지 않는 ID면 예외가 발생한다.")
    @Test
    void should_throwCoreException_when_getNonExistingBrand() {
        // given
        Long brandId = 1L;
        given(brandRepository.get(brandId)).willReturn(Optional.empty());

        // when & then
        CoreException coreException = assertThrows(CoreException.class,
            () -> brandService.getBrand(brandId));
        assertThat(coreException.getCoreErrorCode()).isEqualTo(BrandErrorCode.BRAND_NOT_FOUND);
    }

    @DisplayName("브랜드 조회 시, 존재하는 ID면 브랜드를 반환한다.")
    @Test
    void should_returnBrand_when_existingBrandId() {
        // given
        Long brandId = 1L;
        Brand existingBrand = new Brand(new CreateBrandCommand("brandName"));
        given(brandRepository.get(brandId)).willReturn(Optional.of(existingBrand));

        // when
        Brand result = brandService.getBrand(brandId);

        // then
        assertThat(result.getBrandId()).isEqualTo(existingBrand.getBrandId());
        assertThat(result.getBrandName()).isEqualTo(existingBrand.getBrandName());
    }
}