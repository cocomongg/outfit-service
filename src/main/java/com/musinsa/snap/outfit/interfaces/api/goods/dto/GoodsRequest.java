package com.musinsa.snap.outfit.interfaces.api.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GoodsRequest {

    @Getter
    @NoArgsConstructor
    public static class GoodsCreateRequest {
        @NotNull
        @Schema(description = "브랜드 ID", example = "1")
        private Long brandId;

        @NotNull
        @Schema(description = "카테고리 ID", example = "1")
        private Long categoryId;

        @NotBlank
        @Schema(description = "상품명", example = "상의1")
        private String goodsName;

        @NotNull
        @Schema(description = "상품가격", example = "1000")
        private Long price;

        @NotNull
        @Min(1)
        @Schema(description = "상품수량", example = "100")
        private Long quantity;
    }

    @Getter
    @Setter
    public static class GetGoodsListRequest {
        @Schema(description = "브랜드 ID", example = "1", nullable = true)
        private Long brandId;

        @Min(0)
        @Schema(description = "페이지 번호", example = "0")
        private int pageNo;

        @Min(10)
        @Schema(description = "페이지 크기", example = "10")
        private int pageSize;
    }

    @Getter
    @NoArgsConstructor
    public static class GoodsUpdateRequest {
        @NotBlank
        @Schema(description = "상품명", example = "상의1")
        private String goodsName;

        @NotNull
        @Schema(description = "상품가격", example = "1000")
        private Long price;

        @Min(1)
        @Schema(description = "상품수량", example = "100")
        private Long quantity;
    }

}
