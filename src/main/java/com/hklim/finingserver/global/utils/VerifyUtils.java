package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class VerifyUtils {
    // 이메일 형식 검증
    public boolean isAvailableEmailFormat(String email) {
        log.info("[VERIFY-FORMAT] Email format verify start. email : {}", email);
        String emailRegex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return matcher.matches();
        } else {
            throw new ApplicationErrorException(ApplicationErrorType.FORMAT_VERIFY_ERROR, "[EMAIL-FORMAT-VERIFY] This email format is unavailable.");
        }
    }
    // 핸드폰 형식 검증
    public boolean isAvailablePhoneNumberFormat(String phoneNumber) {
        log.info("[VERIFY-FORMAT] Phone number format verify start. phone number : {}", phoneNumber);
        String phoneNumberRegex = "^\\d{3}-\\d{3,4}-\\d{4}$";
        Pattern pattern = Pattern.compile(phoneNumberRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            return matcher.matches();
        } else {
            throw new ApplicationErrorException(ApplicationErrorType.FORMAT_VERIFY_ERROR, "[PHONE-FORMAT-VERIFY] This phone number format is unavailable.");
        }
    }

    // 비밀번호 형식 검증
    public boolean isAvailablePasswordFormat(String phoneNumber) {
        log.info("[VERIFY-FORMAT] Password format verify start. ");
        String phoneNumberRegex = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
        Pattern pattern = Pattern.compile(phoneNumberRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            return matcher.matches();
        } else {
            throw new ApplicationErrorException(ApplicationErrorType.FORMAT_VERIFY_ERROR, "[PASSWORD-FORMAT-VERIFY] This password format is unavailable.");
        }
    }
}
