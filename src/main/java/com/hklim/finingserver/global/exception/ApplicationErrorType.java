package com.hklim.finingserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplicationErrorType {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYSTEM ERROR OCCURRED")
    ,EMPTY_RESULT_DATA_ERROR(HttpStatus.BAD_REQUEST, "FAIL TO FIND RESULT DATA, TRY AGAIN")
    ,INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, "INVALID DATA ARGUMENT")

    ,DATA_DUPLICATED_ERROR(HttpStatus.BAD_REQUEST, "DATA IS DUPLICATED")
    ,DATA_ENCRPYT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FAIL TO ENCRYPT DATA" )

    ,AUTHCODE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FAIL TO SEND AUTH CODE")
    ,DATA_STORAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FAIL TO STORAGE DATA")
    ,DATA_MATCHING_FAIL(HttpStatus.BAD_REQUEST, "FAIL TO MATCH DATA")

    ,NO_SUCH_MEMBER_ERROR(HttpStatus.NOT_FOUND, "NO SUCH MEMBER EXISTS");



    private HttpStatus httpStatus;
    private String message;

    public int getStatusCode() {
        return httpStatus.value();
    }
}