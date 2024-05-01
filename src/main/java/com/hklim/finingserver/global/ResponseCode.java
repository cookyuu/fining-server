package com.hklim.finingserver.global;

public enum ResponseCode {

    // Common Code
    SUCCESS("성공"),

    BAD_REQUEST("잘못된 요청입니다."),
    UNAUTHORIZED("인증이 필요한 요청입니다."),
    FORBIDDEN("허용되지 않은 접근입니다."),
    NOT_FOUND("데이터가 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
    ;

    private final String message;

    ResponseCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
