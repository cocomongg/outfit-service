package com.musinsa.snap.outfit.domain.category.error;

import com.musinsa.snap.outfit.common.error.CoreErrorCode;
import com.musinsa.snap.outfit.common.error.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements CoreErrorCode {
    CATEGORY_NOT_FOUND(ErrorType.NOT_FOUND, "카테고리를 찾을 수 없습니다.");

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
