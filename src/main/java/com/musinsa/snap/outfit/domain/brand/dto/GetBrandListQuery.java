package com.musinsa.snap.outfit.domain.brand.dto;

import com.musinsa.snap.outfit.interfaces.api.brand.dto.BrandRequest.GetBrandListRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetBrandListQuery {
    private final int pageNo;
    private final int pageSize;
}
