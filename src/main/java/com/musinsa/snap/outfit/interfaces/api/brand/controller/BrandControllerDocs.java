package com.musinsa.snap.outfit.interfaces.api.brand.controller;

import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.CreateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.GetBrandListRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.UpdateBrandRequest;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandCreateResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandDetailResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.BrandListResponse;
import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandResponse.SingleBrandLowestPriceResponse;
import com.musinsa.snap.outfit.interfaces.api.common.response.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;

@Tag(name = "브랜드 API", description = "브랜드 관련 API")
public interface BrandControllerDocs {

    @Operation(summary = "브랜드 추가", description = "새로운 브랜드를 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "브랜드 추가 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INVALID_REQUEST",
                        "message": "유효하지 않은 값입니다."
                    }
                    """))),
        @ApiResponse(responseCode = "400", description = "브랜드 이름 중복",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "BRAND_NAME_ALREADY_EXISTS",
                        "message": "브랜드 이름이 이미 존재합니다."
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
    ApiSuccessResponse<BrandCreateResponse> createBrand(
         @Schema(description = "브랜드 추가 요청") CreateBrandRequest request);

    @Operation(summary = "브랜드 목록 조회", description = "브랜드 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "브랜드 목록 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<BrandListResponse> getBrandList(@ParameterObject GetBrandListRequest request);

    @Operation(summary = "브랜드 상세 조회", description = "특정 브랜드의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "브랜드 상세 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<BrandDetailResponse> getBrandDetail(
        @Parameter(description = "브랜드 ID") Long brandId);

    @Operation(summary = "브랜드 정보 수정", description = "특정 브랜드의 정보를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "브랜드 정보 수정 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INVALID_REQUEST",
                        "message": "유효하지 않은 값입니다."
                    }
                    """))),
        @ApiResponse(responseCode = "400", description = "브랜드 이름 중복",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "BRAND_NAME_ALREADY_EXISTS",
                        "message": "브랜드 이름이 이미 존재합니다."
                    }
                    """))),
        @ApiResponse(responseCode = "404", description = "브랜드를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "BRAND_NOT_FOUND",
                        "message": "브랜드를 찾을 수 없습니다."
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
    ApiSuccessResponse<BrandDetailResponse> updateBrand(
        @Parameter(description = "브랜드 ID") Long brandId,
        @Schema(description = "브랜드 정보 수정 요청") UpdateBrandRequest request);

    @Operation(summary = "브랜드 삭제", description = "특정 브랜드를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "브랜드 삭제 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "404", description = "브랜드를 찾을 수 없음",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "BRAND_NOT_FOUND",
                        "message": "브랜드를 찾을 수 없습니다."
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
    ApiSuccessResponse<?> deleteBrand(@Parameter(description = "브랜드 ID") Long brandId);

    @Operation(summary = "단일 브랜드 전체 최저 가격 조회",
        description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "최저 가격 조회 성공",
            useReturnTypeSchema = true),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "code": "INTERNAL_SERVER_ERROR",
                        "message": "서버 에러가 발생했습니다."
                    }
                    """))),
    })
    ApiSuccessResponse<SingleBrandLowestPriceResponse> getBrandLowestPrice();
}