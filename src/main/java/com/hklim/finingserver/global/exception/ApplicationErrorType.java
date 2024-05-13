package com.hklim.finingserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplicationErrorType {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "System error occurred. ")
    ,EMPTY_RESULT_DATA_ERROR(HttpStatus.BAD_REQUEST, "Fail to find Result Data, Try again. ")
    ,INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, "Invalid Data Argument. ")

    ,DATA_DUPLICATED_ERROR(HttpStatus.BAD_REQUEST, "Data is Duplicated")
    ,DATA_ENCRPYT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to Encrypt Data. " )

    ,AUTHCODE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to send Auth Code")
    ,FAIL_TO_SAVE_DATA(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to save Data")
    ,DATA_MATCHING_FAIL(HttpStatus.BAD_REQUEST, "Fail to match Data. ")

    ,NO_MEMBER_EXIST(HttpStatus.BAD_REQUEST, "Not Exists Member. ")
    ,FAIL_TO_FIND_MEMBER(HttpStatus.BAD_REQUEST, "Fail to find Member. ")
    ,FAIL_TO_FIND_DATA(HttpStatus.BAD_REQUEST, "Fail to find Data. ");

    private HttpStatus httpStatus;
    private String message;

    public int getStatusCode() {
        return httpStatus.value();
    }
}