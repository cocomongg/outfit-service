package com.musinsa.snap.outfit.interfaces.api.category.controller;

import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoriesLowestPriceResponse;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryExtremePricesResponse;
import com.musinsa.snap.outfit.interfaces.api.category.dto.CategoryResponse.CategoryListResponse;
import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "카테고리 API", description = "카테고리 관련 API")
public interface CategoryControllerDocs {

    @Operation(summary = "카테고리 목록 조회", description = "모든 카테고리 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<CategoryListResponse> getCategoryList();

    @Operation(summary = "카테고리별 최저가격 조회",
        description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "카테고리별 최저가격 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<CategoriesLowestPriceResponse> getCategoriesLowestPrice();

    @Operation(summary = "카테고리 이름별 최저/최고 가격 조회",
        description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "가격 범위 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INVALID_REQUEST",
                        "message": "유효하지 않은 값입니다."
                    }
                    """))),
        @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "CATEGORY_NOT_FOUND",
                        "message": 카테고리를 찾을 수 없습니다."
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
    ApiSuccessResponse<CategoryExtremePricesResponse> getCategoryLowestAndHighestPrices(
        @Parameter(description = "카테고리 이름", required = true) String categoryName);
}