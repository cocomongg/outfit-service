package com.musinsa.snap.outfit.domain.goods.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.musinsa.snap.outfit.common.error.CoreException;
import com.musinsa.snap.outfit.domain.goods.dto.CreateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.dto.UpdateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.error.GoodsErrorCode;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.repository.GoodsRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GoodsServiceTest {

    @Mock
    private GoodsRepository goodsRepository;

    @InjectMocks
    private GoodsService goodsService;

    @DisplayName("상품 생성 시, 이미 존재하는 상품명이 아니면 저장하고 반환한다.")
    @Test
    void should_saveAndReturnGoods_when_givenCommand() {
        // given
        CreateGoodsCommand command = new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods givenGoods = new Goods(command);
        given(goodsRepository.existsByGoodsName(command.getGoodsName()))
            .willReturn(false);
        given(goodsRepository.save(any(Goods.class)))
            .willReturn(givenGoods);

        // when
        Goods result = goodsService.createGoods(command);

        // then
        assertThat(result.getGoodsId()).isEqualTo(givenGoods.getGoodsId());
        assertThat(result.getGoodsName()).isEqualTo(givenGoods.getGoodsName());
        assertThat(result.getPrice()).isEqualTo(givenGoods.getPrice());
        assertThat(result.getQuantity()).isEqualTo(givenGoods.getQuantity());
        assertThat(result.getBrandId()).isEqualTo(givenGoods.getBrandId());
        assertThat(result.getCategoryId()).isEqualTo(givenGoods.getCategoryId());
    }

    @DisplayName("상품 생성 시, 이미 존재하는 상품명이면, CoreException이 발생한다.")
    @Test
    void should_throwCoreException_when_CreateWithExistsGoodsName() {
        // given
        String goodsName = "name";
        CreateGoodsCommand command = new CreateGoodsCommand(goodsName, 1000L, 10L, 1L, 1L);
        given(goodsRepository.existsByGoodsName(goodsName))
            .willReturn(true);

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> goodsService.createGoods(command));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(GoodsErrorCode.GOODS_NAME_ALREADY_EXISTS);
    }

    @DisplayName("상품 이름 중복 검증 시, 이미 존재하면 CoreException이 발생한다.")
    @Test
    void should_throwCoreException_when_validateGoodsNameIsExists() {
        // given
        String goodsName = "name";
        given(goodsRepository.existsByGoodsName(goodsName))
            .willReturn(true);

        // when & then
        CoreException coreException = assertThrows(CoreException.class,
            () -> goodsService.validateGoodsNameNotExists(goodsName));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(GoodsErrorCode.GOODS_NAME_ALREADY_EXISTS);
    }

    @DisplayName("상품 이름 중복 검증 시, 존재하지 않으면 예외가 발생하지 않는다.")
    @Test
    void should_notThrowException_when_validateGoodsNameNotExists() {
        // given
        String goodsName = "name";
        given(goodsRepository.existsByGoodsName(goodsName))
            .willReturn(false);

        // when & then
        assertDoesNotThrow(() -> goodsService.validateGoodsNameNotExists(goodsName));
    }

    @DisplayName("상품 수정 시, 존재하지 않는 ID면 예외가 발생한다.")
    @Test
    void should_throwCoreException_when_updateNonExistingGoods() {
        // given
        Long goodsId = 1L;
        String goodsName = "name";
        UpdateGoodsCommand command = new UpdateGoodsCommand(goodsId, goodsName, 1000L, 10L);
        given(goodsRepository.get(goodsId))
            .willReturn(Optional.empty());

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> goodsService.updateGoods(command));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(GoodsErrorCode.GOODS_NOT_FOUND);
    }

    @DisplayName("상품 수정 시, 기존 이름과 같은 입력이라면 이름 중복 검사를 하지 않는다.")
    @Test
    void should_updateGoods_when_nameUnchanged() {
        // given
        Long goodsId = 1L;
        String goodsName = "name";
        CreateGoodsCommand createGoodsCommand =
            new CreateGoodsCommand(goodsName, 1000L, 10L, 1L, 1L);
        Goods existingGoods = new Goods(createGoodsCommand);
        given(goodsRepository.get(1L)).willReturn(Optional.of(existingGoods));

        UpdateGoodsCommand updateGoodsCommand = new UpdateGoodsCommand(goodsId,
            goodsName, 2000L, 20L);

        // when
        Goods result = goodsService.updateGoods(updateGoodsCommand);

        // then
        assertThat(result.getGoodsId()).isEqualTo(existingGoods.getGoodsId());
        assertThat(result.getGoodsName()).isEqualTo(existingGoods.getGoodsName());
        assertThat(result.getPrice()).isEqualTo(updateGoodsCommand.getPrice());
        assertThat(result.getQuantity()).isEqualTo(updateGoodsCommand.getQuantity());
        then(goodsRepository).should(never()).existsByGoodsName(any(String.class));
    }

    @DisplayName("상품 수정 시, 새로운 이름이 이미 존재하면 예외가 발생한다.")
    @Test
    void should_throwCoreException_when_updateWithExistsGoodsName() {
        // given
        Long goodsId = 1L;
        String goodsName = "name";
        CreateGoodsCommand createGoodsCommand =
            new CreateGoodsCommand(goodsName, 1000L, 10L, 1L, 1L);
        Goods existingGoods = new Goods(createGoodsCommand);
        given(goodsRepository.get(goodsId)).willReturn(Optional.of(existingGoods));

        UpdateGoodsCommand updateGoodsCommand = new UpdateGoodsCommand(goodsId, "newName", 2000L, 20L);
        given(goodsRepository.existsByGoodsName(updateGoodsCommand.getGoodsName()))
            .willReturn(true);

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> goodsService.updateGoods(updateGoodsCommand));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(GoodsErrorCode.GOODS_NAME_ALREADY_EXISTS);
    }

    @DisplayName("상품 수정 시, 새로운 이름이 존재하지 않는다면 업데이트한다.")
    @Test
    void should_updateGoods_when_validNewName() {
        // given
        String newGoodsName = "newName";
        CreateGoodsCommand createGoodsCommand =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods existingGoods = new Goods(createGoodsCommand);
        given(goodsRepository.get(1L)).willReturn(Optional.of(existingGoods));

        UpdateGoodsCommand updateGoodsCommand = new UpdateGoodsCommand(1L, newGoodsName, 2000L, 20L);
        given(goodsRepository.existsByGoodsName(newGoodsName))
            .willReturn(false);

        // when
        Goods result = goodsService.updateGoods(updateGoodsCommand);

        // then
        assertThat(result.getGoodsId()).isEqualTo(existingGoods.getGoodsId());
        assertThat(result.getGoodsName()).isEqualTo(newGoodsName);
        assertThat(result.getPrice()).isEqualTo(updateGoodsCommand.getPrice());
        assertThat(result.getQuantity()).isEqualTo(updateGoodsCommand.getQuantity());
    }

    @DisplayName("상품 삭제 시, 존재하지 않는 ID면 예외가 발생한다.")
    @Test
    void should_throwCoreException_when_deleteNonExistingGoods() {
        // given
        Long goodsId = 1L;
        given(goodsRepository.get(goodsId))
            .willReturn(Optional.empty());

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> goodsService.deleteGoods(goodsId));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(GoodsErrorCode.GOODS_NOT_FOUND);
    }

    @DisplayName("상품 삭제 시, 존재하는 ID면 삭제된다.")
    @Test
    void should_deleteGoods_when_existingGoodsId() {
        // given
        Long goodsId = 1L;
        CreateGoodsCommand createGoodsCommand =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods existingGoods = new Goods(createGoodsCommand);
        given(goodsRepository.get(goodsId)).willReturn(Optional.of(existingGoods));

        // when
        goodsService.deleteGoods(goodsId);

        // then
        assertThat(existingGoods.isDeleted()).isTrue();
    }

    @DisplayName("상품 조회 시, 존재하지 않는 ID면 CoreException이 발생한다.")
    @Test
    void should_throwCoreException_when_getNonExistingGoods() {
        // given
        Long goodsId = 1L;
        given(goodsRepository.get(goodsId))
            .willReturn(Optional.empty());

        // when, then
        CoreException coreException = assertThrows(CoreException.class,
            () -> goodsService.getGoods(goodsId));
        assertThat(coreException.getCoreErrorCode())
            .isEqualTo(GoodsErrorCode.GOODS_NOT_FOUND);
    }

    @DisplayName("상품 조회 시, 존재하는 ID면 상품을 반환한다.")
    @Test
    void should_returnGoods_when_existingGoodsId() {
        // given
        Long goodsId = 1L;
        CreateGoodsCommand createGoodsCommand =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods existingGoods = new Goods(createGoodsCommand);
        given(goodsRepository.get(goodsId)).willReturn(Optional.of(existingGoods));

        // when
        Goods result = goodsService.getGoods(goodsId);

        // then
        assertThat(result.getGoodsId()).isEqualTo(existingGoods.getGoodsId());
        assertThat(result.getGoodsName()).isEqualTo(existingGoods.getGoodsName());
        assertThat(result.getPrice()).isEqualTo(existingGoods.getPrice());
        assertThat(result.getQuantity()).isEqualTo(existingGoods.getQuantity());
    }
}