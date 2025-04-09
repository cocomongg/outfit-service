package com.musinsa.snap.outfit.infrastructure.goods;

import static org.assertj.core.api.Assertions.assertThat;

import com.musinsa.snap.outfit.config.JPAConfig;
import com.musinsa.snap.outfit.domain.common.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.CreateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.domain.goods.repository.GoodsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({GoodsRepositoryImpl.class, GoodsQueryRepository.class, JPAConfig.class})
class GoodsRepositoryImplTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsJpaRepository goodsJpaRepository;

    @DisplayName("삭제되지 않은 상품ID로 조회 시, ID에 해당하는 상품을 반환한다")
    @Test
    void should_returnGoods_when_notDeletedGoodsId() {
        // given
        CreateGoodsCommand command =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods goods = new Goods(command);
        goodsJpaRepository.save(goods);

        // when
        Optional<Goods> optionalGetGoods = goodsRepository.get(goods.getGoodsId());

        // then
        assertThat(optionalGetGoods).isPresent();
        Goods getGoods = optionalGetGoods.get();
        assertThat(getGoods.getGoodsId()).isEqualTo(goods.getGoodsId());
    }

    @DisplayName("삭제된 상품ID로 조회 시, 조회되지 않는다.")
    @Test
    void should_returnEmptyOptional_when_deletedGoodsId() {
        // given
        CreateGoodsCommand command =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods goods = new Goods(command);
        goods.delete();
        goodsJpaRepository.save(goods);

        // when
        Optional<Goods> optionalGetGoods = goodsRepository.get(goods.getGoodsId());

        // then
        assertThat(optionalGetGoods).isNotPresent();
    }

    @DisplayName("상품 목록 조회 시, 삭제되지 않은 상품 목록을 반환한다.")
    @Test
    void should_returnNotDeletedPagedGoodsList_when_byQuery() {
        // given
        List<Goods> existsGoodsList = new ArrayList<>();
        List<Goods> deletedGoodsList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            CreateGoodsCommand command =
                new CreateGoodsCommand("name" + i, 1000L, 10L, 1L, 1L);
            Goods goods = new Goods(command);
            existsGoodsList.add(goods);
        }
        for (int i = 0; i < 5; i++) {
            CreateGoodsCommand command =
                new CreateGoodsCommand("deleted" + i, 1000L, 10L, 1L, 1L);
            Goods goods = new Goods(command);
            goods.delete();
            deletedGoodsList.add(goods);
        }

        goodsJpaRepository.saveAll(existsGoodsList);
        goodsJpaRepository.saveAll(deletedGoodsList);

        // when
        GetGoodsListQuery query = new GetGoodsListQuery(0, 10, 1L);
        PageResult<Goods> result = goodsRepository.getList(query);

        // then
        assertThat(result.getTotalElements()).isEqualTo(existsGoodsList.size());
        assertThat(result.getTotalPages()).isEqualTo(existsGoodsList.size() / query.getPageSize() + 1);
        assertThat(result.getPageNo()).isEqualTo(query.getPageNo());
        assertThat(result.getPageSize()).isEqualTo(query.getPageSize());

        List<Goods> resultContent = result.getContent();
        for(Goods goods : resultContent) {
            assertThat(goods.isDeleted()).isFalse();
            assertThat(goods.getBrandId()).isEqualTo(query.getBrandId());
        }
    }

    @DisplayName("상품 목록 조회 시, 최근 생성된 순으로 정렬된다.")
    @Test
    void should_returnSortedPagedGoodsList_when_byQuery() {
        // given
        CreateGoodsCommand command1 =
            new CreateGoodsCommand("name1", 1000L, 10L, 1L, 1L);
        CreateGoodsCommand command2 =
            new CreateGoodsCommand("name2", 1000L, 10L, 1L, 1L);
        CreateGoodsCommand command3 =
            new CreateGoodsCommand("name1", 1000L, 10L, 1L, 1L);
        Goods goods1 = goodsJpaRepository.save(new Goods(command1));
        Goods goods2 = goodsJpaRepository.save(new Goods(command2));
        Goods goods3 = goodsJpaRepository.save(new Goods(command3));

        // when
        GetGoodsListQuery query = new GetGoodsListQuery(0, 10, 1L);
        PageResult<Goods> result = goodsRepository.getList(query);

        // then
        List<Goods> resultContent = result.getContent();
        assertThat(resultContent.size()).isEqualTo(3);
        assertThat(resultContent.get(0).getGoodsId()).isEqualTo(goods3.getGoodsId());
        assertThat(resultContent.get(1).getGoodsId()).isEqualTo(goods2.getGoodsId());
        assertThat(resultContent.get(2).getGoodsId()).isEqualTo(goods1.getGoodsId());
    }

    @DisplayName("검색 조건에 브랜드Id가 없으면, 상품 목록 조회 시 모든 상품을 반환한다.")
    @Test
    void should_returnAllPagedGoodsList_when_noBrandId() {
        // given
        List<Goods> existsGoodsList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            CreateGoodsCommand command =
                new CreateGoodsCommand("name" + i, 1000L, 10L, (long) i, 1L);
            Goods goods = new Goods(command);
            existsGoodsList.add(goods);
        }
        goodsJpaRepository.saveAll(existsGoodsList);

        // when
        GetGoodsListQuery query = new GetGoodsListQuery(0, 10, null);
        PageResult<Goods> result = goodsRepository.getList(query);

        // then
        assertThat(result.getTotalElements()).isEqualTo(existsGoodsList.size());
        assertThat(result.getTotalPages()).isEqualTo(existsGoodsList.size() / query.getPageSize() + 1);
        assertThat(result.getPageNo()).isEqualTo(query.getPageNo());
        assertThat(result.getPageSize()).isEqualTo(query.getPageSize());
        List<Goods> resultContent = result.getContent();
    }

    @DisplayName("상품 이름 존재 여부 조회 시, 존재하면 true를 반환한다.")
    @Test
    void should_returnTrue_when_existsGoodsName() {
        // given
        CreateGoodsCommand command =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods goods = new Goods(command);
        goodsJpaRepository.save(goods);

        // when
        boolean result = goodsRepository.existsByGoodsName(command.getGoodsName());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상품 이름 존재 여부 조회 시, 존재하지 않으면 false를 반환한다.")
    @Test
    void should_returnFalse_when_notExistsGoodsName() {
        // given
        String nonExistentBrandName = "nonExistentBrandName";

        // when
        boolean result = goodsRepository.existsByGoodsName(nonExistentBrandName);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 이름 존재 여부 조회 시, 존재하는 상품 이름이 삭제된 경우 false를 반환한다.")
    @Test
    void should_returnFalse_when_existsGoodsNameButDeleted() {
        // given
        CreateGoodsCommand command =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods goods = new Goods(command);
        goods.delete();
        goodsJpaRepository.save(goods);

        // when
        boolean result = goodsRepository.existsByGoodsName(command.getGoodsName());

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void should_saveGoods () {
        // given
        CreateGoodsCommand command =
            new CreateGoodsCommand("name", 1000L, 10L, 1L, 1L);
        Goods goods = new Goods(command);

        // when
        Goods result = goodsRepository.save(goods);

        // then
        assertThat(result.getBrandId()).isNotNull();

        Optional<Goods> optionalGetGoods = goodsRepository.get(result.getGoodsId());
        assertThat(optionalGetGoods.isPresent()).isTrue();
        Goods getGoods = optionalGetGoods.get();
        assertThat(getGoods.getGoodsId()).isEqualTo(result.getGoodsId());
        assertThat(getGoods.getGoodsName()).isEqualTo(command.getGoodsName());
        assertThat(getGoods.getPrice()).isEqualTo(command.getPrice());
        assertThat(getGoods.getQuantity()).isEqualTo(command.getQuantity());
        assertThat(getGoods.getBrandId()).isEqualTo(command.getBrandId());
        assertThat(getGoods.getCategoryId()).isEqualTo(command.getCategoryId());
    }
}