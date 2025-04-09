package com.musinsa.snap.outfit.interfaces.api.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BrandRequest {

    @Getter
    @NoArgsConstructor
    public static class CreateBrandRequest {
        @NotBlank
        @Schema(description = "브랜드명", example = "Z", requiredMode = RequiredMode.REQUIRED)
        private String brandName;
    }

    @Getter
    @Setter
    public static class GetBrandListRequest {
        @Min(0)
        @Schema(description = "페이지 번호", example = "0")
        private int pageNo;

        @Min(10)
        @Max(100)
        @Schema(description = "페이지 크기", example = "10")
        private int pageSize;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateBrandRequest {
        @NotBlank
        @Schema(description = "브랜드명", example = "A", requiredMode = RequiredMode.REQUIRED)
        private String brandName;
    }
}
