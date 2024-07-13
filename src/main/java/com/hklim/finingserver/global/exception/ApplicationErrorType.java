package com.hklim.finingserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplicationErrorType {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "System error occurred. ")
    ,REDIS_ERROR(HttpStatus.BAD_REQUEST, "Redis error occurred. ")
    ,EMPTY_RESULT_DATA_ERROR(HttpStatus.NOT_FOUND, "Fail to find Result Data, Try again. ")
    ,INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, "Invalid Data Argument. ")

    ,DATA_DUPLICATED_ERROR(HttpStatus.BAD_REQUEST, "Data is Duplicated")
    ,DATA_ENCRPYT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to Encrypt Data. " )

    ,AUTHCODE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to send Auth Code. ")
    ,FAIL_TO_SAVE_DATA(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to save Data. ")
    ,DATA_MATCHING_FAIL(HttpStatus.NOT_FOUND, "Fail to match Data. ")
    ,ALREADY_WITHDRAWN_MEMBER(HttpStatus.BAD_REQUEST, "Already withdrawn member.")

    ,NOT_FOUND_STOCK(HttpStatus.NOT_FOUND, "Fail to find Stock. Stock is not exists. ")
    ,NOT_FOUND_INDICATORS(HttpStatus.NOT_FOUND, "Fail to find Indicators. Indicators is not exists. ")
    ,NOT_FOUND_PORTFOLIO(HttpStatus.NOT_FOUND, "Fail to find Portfolio. Stock is not exists in Portfolio. ")
    ,NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "Fail to find Member. Member is not exists. ")
    ,NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "Fail to find Data. ")

    ,FAIL_JWT_REISSUE(HttpStatus.BAD_REQUEST, "Fail to reissue accessToken. ")
    ,FAIL_JWT_VALIDATION_LOGOUT(HttpStatus.BAD_REQUEST, "Already logged out jwt. ")
    ,FAIL_JWT_LOGOUT(HttpStatus.INTERNAL_SERVER_ERROR, "JWT token logout Failure. ")
    ,FAIL_JWT_VALIDATION(HttpStatus.UNAUTHORIZED, "Fail to validate JWT Token. ")
    ,FAIL_DATATIME_PARSE(HttpStatus.BAD_REQUEST, "Fail to parse DateTime. String convert to LocalDate. ")
    ,FAIL_CRAWLING_SAVE(HttpStatus.BAD_REQUEST, "Fail to save data during crawling process. ")
    ,FAIL_WITHDRAWAL_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to withdrawal member. ");



    private HttpStatus httpStatus;
    private String message;

    public int getStatusCode() {
        return httpStatus.value();
    }
}