package com.musinsa.snap.outfit.domain.brand.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateBrandCommand {
    private final String brandName;
}
