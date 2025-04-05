package com.musinsa.snap.outfit.interfaces.api.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseMeta {
    private final String result;
    private final String message;

    public static ResponseMeta success() {
        return new ResponseMeta("SUCCESS", "성공적으로 처리하였습니다.");
    }
}
