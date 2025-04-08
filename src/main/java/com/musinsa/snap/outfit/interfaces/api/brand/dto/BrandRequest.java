package com.musinsa.snap.outfit.interfaces.api.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BrandRequest {

    @Getter
    @NoArgsConstructor
    public static class CreateBrandRequest {
        @NotBlank
        @Schema(description = "브랜드명", example = "Z", requiredMode = RequiredMode.REQUIRED)
        private String brandName;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateBrandRequest {
        @NotBlank
        @Schema(description = "브랜드명", example = "A", requiredMode = RequiredMode.REQUIRED)
        private String brandName;
    }

}
