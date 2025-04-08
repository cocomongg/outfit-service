package com.musinsa.snap.outfit.interfaces.api.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GoodsRequest {

    @Getter
    @NoArgsConstructor
    public static class GoodsCreateRequest {
        @NotNull
        @Schema(description = "브랜드 ID", example = "1")
        private Long brandId;

        @NotBlank
        @Schema(description = "카테고리 code", example = "001")
        private String categoryCode;

        @NotBlank
        @Schema(description = "상품명", example = "상의1")
        private String goodsName;

        @NotNull
        @Schema(description = "상품가격", example = "상의1")
        private Long price;
    }

    @Getter
    @NoArgsConstructor
    public static class GoodsUpdateRequest {
        @NotBlank
        @Schema(description = "상품명", example = "상의1")
        private String goodsName;

        @NotNull
        @Schema(description = "상품가격", example = "상의1")
        private Long price;
    }

}
