package com.musinsa.snap.outfit.domain.brand.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateBrandCommand {
    private final Long brandId;
    private final String brandName;
}
