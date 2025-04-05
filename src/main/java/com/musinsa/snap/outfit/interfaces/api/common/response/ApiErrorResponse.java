package com.musinsa.snap.outfit.interfaces.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private final String code;
    private final String message;
}

