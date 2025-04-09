package com.musinsa.snap.outfit.interfaces.api.goods.controller;

import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GetGoodsListRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsCreateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsRequest.GoodsUpdateRequest;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.goods.dto.GoodsResponse.GoodsListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;

@Tag(name = "상품 API", description = "상품 관련 API")
public interface GoodsControllerDocs {

    @Operation(summary = "상품 추가", description = "새로운 상품을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "상품 추가 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INVALID_REQUEST",
                        "message": "유효하지 않은 값입니다."
                    }
                    """))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<GoodsCreateResponse> createGoods(
        @Schema(description = "상품 추가 요청") GoodsCreateRequest request);

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<GoodsListResponse> getGoodsList(@ParameterObject GetGoodsListRequest request);

    @Operation(summary = "상품 상세 조회", description = "특정 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "GOODS_NOT_FOUND",
                        "message": "상품을 찾을 수 없습니다."
                    }
                    """))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<GoodsDetailResponse> getGoodsDetail(
        @Parameter(description = "상품 ID") Long goodsId);

    @Operation(summary = "상품 정보 수정", description = "특정 상픔의 정보를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 정보 수정 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INVALID_REQUEST",
                        "message": "유효하지 않은 값입니다."
                    }
                    """))),
        @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "GOODS_NOT_FOUND",
                        "message": "상품을 찾을 수 없습니다."
                    }
                    """))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<GoodsDetailResponse> updateGoods(
        @Parameter(description = "상품 ID") Long goodsId,
        @Schema(description = "상품 정보 수정 요청") GoodsUpdateRequest request);

    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 삭제 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "GOODS_NOT_FOUND",
                        "message": "상품을 찾을 수 없습니다."
                    }
                    """))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<?> deleteGoods(@Parameter(description = "상품 ID") Long goodsId);
}
