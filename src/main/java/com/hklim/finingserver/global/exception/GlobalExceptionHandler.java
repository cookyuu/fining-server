package com.hklim.finingserver.global.exception;

import com.hklim.finingserver.global.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeParseException;

@Primary
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApplicationErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationErrorException(WebRequest request, ApplicationErrorException e) {
        String errMsg = "";
        if (e.getMessage() == null) {
            errMsg = e.getErrorType().getMessage();
            log.error("[ApplicationErrorException] {}", errMsg);
        } else {
            errMsg = e.getMessage();
            log.error("[ApplicationErrorException] {}", errMsg);
        }
        var response = new ErrorResponseDto(e.getErrorType().name(), errMsg);
        return new ResponseEntity<>(response, e.getErrorType().getHttpStatus());
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleEmptyResultDataAccessException(WebRequest request, EmptyResultDataAccessException e) {
        log.error("[EmptyResultDataAccessException] {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.EMPTY_RESULT_DATA_ERROR.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.EMPTY_RESULT_DATA_ERROR.getHttpStatus());
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(WebRequest request, ValidationException e) {
        log.error("[ValidationException] {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INVALID_DATA_ARGUMENT.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INVALID_DATA_ARGUMENT.getHttpStatus());
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    public ResponseEntity<Object> handleHttpMessageConversionException(WebRequest request, HttpMessageConversionException e) {
        log.error("[HttpMessageConversionException] {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INVALID_DATA_ARGUMENT.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INVALID_DATA_ARGUMENT.getHttpStatus());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(WebRequest request, ConstraintViolationException e) {
        log.error("[ConstraintViolationException] {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INVALID_DATA_ARGUMENT.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INVALID_DATA_ARGUMENT.getHttpStatus());
    }


    @ExceptionHandler(value = DateTimeParseException.class)
    public ResponseEntity<Object> handleConstraintDateTimeParseException(WebRequest request, DateTimeParseException e) {
        log.error("[DateTimeParseException] {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.FAIL_DATATIME_PARSE.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INVALID_DATA_ARGUMENT.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(WebRequest request, Exception e) {
        log.error("[Exception] {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INTERNAL_ERROR.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INTERNAL_ERROR.getHttpStatus());
    }
}