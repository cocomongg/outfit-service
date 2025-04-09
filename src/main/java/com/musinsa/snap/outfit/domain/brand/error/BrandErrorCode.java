package com.musinsa.snap.outfit.domain.brand.error;

import com.musinsa.snap.outfit.common.error.CoreErrorCode;
import com.musinsa.snap.outfit.common.error.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BrandErrorCode implements CoreErrorCode {
    BRAND_NOT_FOUND(ErrorType.NOT_FOUND, "브랜드를 찾을 수 없습니다."),
    BRAND_NAME_ALREADY_EXISTS(ErrorType.BAD_REQUEST, "브랜드 이름이 이미 존재합니다.");

    private final ErrorType errorType;
    private final String message;

    @Override
    public String getErrorCode() {
        return this.name();
    }

    @Override
    public ErrorType getErrorType() {
        return this.errorType;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
