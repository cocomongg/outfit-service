package com.musinsa.snap.outfit.interfaces.api.goods.controller;

import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandInfo;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryInfo;
import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsCreateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsUpdateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsInfo;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsListResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/goods")
@RestController
public class GoodsController implements GoodsControllerDocs {

    @Override
    @PostMapping
    public ApiSuccessResponse<GoodsCreateResponse> createGoods(@Valid @RequestBody GoodsCreateRequest request) {
        GoodsCreateResponse response = new GoodsCreateResponse(1L);
        return ApiSuccessResponse.CREATED(response);
    }

    @Override
    @GetMapping
    public ApiSuccessResponse<GoodsListResponse> getGoodsList(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize) {
        GoodsInfo goodsInfo1 = new GoodsInfo(1L, "상의1", 10000L);
        GoodsInfo goodsInfo2 = new GoodsInfo(2L, "아우터1", 10000L);

        GoodsListResponse response = new GoodsListResponse(pageNo, pageSize, 2L, 1,
            List.of(goodsInfo1, goodsInfo2));
        return ApiSuccessResponse.OK(response);
    }

    @Override
    @GetMapping("/{goodsId}")
    public ApiSuccessResponse<GoodsDetailResponse> getGoodsDetail(@PathVariable Long goodsId) {
        GoodsDetailResponse response = new GoodsDetailResponse(
            goodsId,
            "상의1",
            10000L,        // 요청값에서 가격을 사용한다고 가정
            LocalDateTime.now(),
            new BrandInfo(1L, "A"),
            new CategoryInfo("001", "상의")
        );
        return ApiSuccessResponse.OK(response);
    }

    @Override
    @PutMapping("/{goodsId}")
    public ApiSuccessResponse<GoodsDetailResponse> updateGoods(@PathVariable Long goodsId,
        @Valid @RequestBody GoodsUpdateRequest request) {
        GoodsDetailResponse response = new GoodsDetailResponse(
            goodsId,
            request.getGoodsName(),
            request.getPrice(),
            LocalDateTime.now(),
            new BrandInfo(1L, "A"),
            new CategoryInfo("001", "상의")
        );
        return ApiSuccessResponse.OK(response);
    }

    @Override
    @DeleteMapping("/{goodsId}")
    public ApiSuccessResponse<?> deleteGoods(@PathVariable Long goodsId) {
        return ApiSuccessResponse.OK();
    }
}
