package com.musinsa.snap.outfit.interfaces.api.goods.controller;

import static com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GetGoodsListRequest;

import com.musinsa.snap.outfit.application.goods.dto.GoodsDetailResult;
import com.musinsa.snap.outfit.application.goods.usecase.CreateGoodsUseCase;
import com.musinsa.snap.outfit.application.goods.usecase.DeleteGoodsUseCase;
import com.musinsa.snap.outfit.application.goods.usecase.GetGoodsDetailUseCase;
import com.musinsa.snap.outfit.application.goods.usecase.GetGoodsListUseCase;
import com.musinsa.snap.outfit.application.goods.usecase.UpdateGoodsUseCase;
import com.musinsa.snap.outfit.domain.common.model.PageResult;
import com.musinsa.snap.outfit.domain.goods.dto.CreateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.dto.GetGoodsListQuery;
import com.musinsa.snap.outfit.domain.goods.dto.GoodsWithBrand;
import com.musinsa.snap.outfit.domain.goods.dto.UpdateGoodsCommand;
import com.musinsa.snap.outfit.domain.goods.model.Goods;
import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsCreateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsUpdateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsListResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.mapper.GoodsMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/goods")
@RestController
public class GoodsController implements GoodsControllerDocs {

    private final CreateGoodsUseCase createGoodsUseCase;
    private final UpdateGoodsUseCase updateGoodsUseCase;
    private final GetGoodsDetailUseCase getGoodsDetailUseCase;
    private final GetGoodsListUseCase getGoodsListUseCase;
    private final DeleteGoodsUseCase deleteGoodsUseCase;

    private final GoodsMapper goodsMapper;

    @Override
    @PostMapping
    public ApiSuccessResponse<GoodsCreateResponse> createGoods(@Valid @RequestBody GoodsCreateRequest request) {
        CreateGoodsCommand command = goodsMapper.toCreateGoodsCommand(request);
        Goods goods = createGoodsUseCase.execute(command);
        GoodsCreateResponse response = goodsMapper.toCreateGoodsResponse(goods);

        return ApiSuccessResponse.CREATED(response);
    }

    @Override
    @GetMapping
    public ApiSuccessResponse<GoodsListResponse> getGoodsList(@Valid GetGoodsListRequest request) {
        GetGoodsListQuery query = goodsMapper.toGetGoodsListQuery(request);
        PageResult<GoodsWithBrand> result = getGoodsListUseCase.execute(query);
        GoodsListResponse response = goodsMapper.toGoodsListResponse(result);

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @GetMapping("/{goodsId}")
    public ApiSuccessResponse<GoodsDetailResponse> getGoodsDetail(@PathVariable Long goodsId) {
        GoodsDetailResult result = getGoodsDetailUseCase.execute(goodsId);
        GoodsDetailResponse response = goodsMapper.toGoodsDetailResponse(result);

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @PutMapping("/{goodsId}")
    public ApiSuccessResponse<GoodsDetailResponse> updateGoods(@PathVariable Long goodsId,
        @Valid @RequestBody GoodsUpdateRequest request) {
        UpdateGoodsCommand command = goodsMapper.toUpdateGoodsCommand(goodsId, request);
        Goods goods = updateGoodsUseCase.execute(command);

        GoodsDetailResult result = getGoodsDetailUseCase.execute(goods.getGoodsId());
        GoodsDetailResponse response = goodsMapper.toGoodsDetailResponse(result);

        return ApiSuccessResponse.OK(response);
    }

    @Override
    @DeleteMapping("/{goodsId}")
    public ApiSuccessResponse<?> deleteGoods(@PathVariable Long goodsId) {
        deleteGoodsUseCase.execute(goodsId);

        return ApiSuccessResponse.OK();
    }
}
