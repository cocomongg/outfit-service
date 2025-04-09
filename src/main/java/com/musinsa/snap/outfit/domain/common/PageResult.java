package com.musinsa.snap.outfit.domain.common;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PageResult<T> {
    private final int pageNo;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final List<T> content;
}
