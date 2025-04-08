package com.musinsa.snap.outfit.interfaces.api.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiSuccessResponse<T> {
    private final ResponseMeta meta;
    private final T data;

    public static ApiSuccessResponse<?> OK() {
        return new ApiSuccessResponse<>(ResponseMeta.success(), null);
    }

    public static <T> ApiSuccessResponse<T> OK(T data) {
        return new ApiSuccessResponse<>(ResponseMeta.success(), data);
    }

    public static <T> ApiSuccessResponse<T> CREATED(T data) {
        return new ApiSuccessResponse<>(ResponseMeta.success(), data);
    }
}
