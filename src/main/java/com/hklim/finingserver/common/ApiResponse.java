package com.hklim.finingserver.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final ResponseCode code;
    private final String message;
    private final T data;

    public ApiResponse(ResponseCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                ResponseCode.SUCCESS,
                ResponseCode.SUCCESS.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                ResponseCode.SUCCESS,
                ResponseCode.SUCCESS.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> failure(ResponseCode code) {
        return new ApiResponse<>(
                code,
                code.getMessage(),
                null
        );
    }
    public static <T> ApiResponse<T> failure(ResponseCode code, String message) {
        return new ApiResponse<>(
                code,
                message,
                null
        );
    }
}
