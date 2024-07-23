package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.global.exception.ApplicationErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("데이터 포멧 검증 테스트")
@SpringBootTest
class VerifyUtilsTest {

    private final VerifyUtils verifyUtils;

    @Autowired
    VerifyUtilsTest(VerifyUtils verifyUtils) {
        this.verifyUtils = verifyUtils;
    }

    @Test
    @DisplayName("정상 포멧 이메일 검증 테스트")
    void givenAvailableEmail_whenVerify_thenReturnTrue() {
        String email = "test@test.com";
        Assertions.assertTrue(verifyUtils.isAvailableEmailFormat(email));
    }
    @Test
    @DisplayName("'@'가 빠진 이메일 검증 테스트")
    void givenRemoveAtEmail_whenVerify_thenReturnApplicationException() {
        String email = "testtest.com";
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailableEmailFormat(email);
        });
    }

    @Test
    @DisplayName("'.'가 빠진 이메일 검증 테스트")
    void givenRemoveDotEmail_whenVerify_thenReturnApplicationException() {
        String email = "test@testcom";
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailableEmailFormat(email);
        });
    }

    @Test
    @DisplayName("ID가 빠진 이메일 검증 테스트")
    void givenRemoveIdEmail_whenVerify_thenReturnApplicationException() {
        String email = "@testcom";
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailableEmailFormat(email);
        });
    }

    // -가 빠진행태
    // 각 자릿수가 안맞을때

    @Test
    @DisplayName("정상 포멧 핸드폰 번호 검증 테스트")
    void givenAvailablePhoneNumber_whenVerify_thenReturnTrue() {
        String phoneNumber1 = "010-000-0000";
        String phoneNumber2 = "010-0000-0000";
        Assertions.assertTrue(verifyUtils.isAvailablePhoneNumberFormat(phoneNumber1));
        Assertions.assertTrue(verifyUtils.isAvailablePhoneNumberFormat(phoneNumber2));
    }
    @Test
    @DisplayName("'-'가 빠진 핸드폰 번호 검증 테스트")
    void givenRemoveDashPhoneNumber_whenVerify_thenReturnApplicationException() {
        String phoneNumber = "01000000000";
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePhoneNumberFormat(phoneNumber);
        });
    }

    @Test
    @DisplayName("각 자릿 수가 다른 핸드폰 번호 검증 테스트")
    void givenDifferentDigitsPhoneNumber_whenVerify_thenReturnApplicationException() {
        String phoneNumber1 = "01-000-0000";
        String phoneNumber2 = "010-00-0000";
        String phoneNumber3 = "010-000-000";

        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePhoneNumberFormat(phoneNumber1);
        });
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePhoneNumberFormat(phoneNumber2);
        });
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePhoneNumberFormat(phoneNumber3);
        });

    }

    @Test
    @DisplayName("정상 포멧 패스워드 검증 테스트 (숫자, 문자, 특수문자 포함 8~15자리 이내)")
    void givenAvailablePassword_whenVerify_thenReturnTrue() {
        String password = "test123!@#";
        Assertions.assertTrue(verifyUtils.isAvailablePasswordFormat(password));
    }

    @Test
    @DisplayName("8~15자리가 아닌 패스워드 검증 테스트")
    void givenMoreOrLessDigitPassword_whenVerify_thenReturnApplicationException() {
        String password1 = "te1!@#";
        String password2 = "testtest12345!@#$%";
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePasswordFormat(password1);
        });
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePasswordFormat(password2);
        });
    }
    @Test
    @DisplayName("특정 문자가 빠진 패스워드 검증 테스트")
    void givenMissingCharacterPassword_whenVerify_thenReturnApplicationException() {
        String password1 = "test!@#test";
        String password2 = "12345!@#$%";
        String password3 = "test12345";
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePasswordFormat(password1);
        });
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePasswordFormat(password2);
        });
        Assertions.assertThrows(ApplicationErrorException.class, () -> {
            verifyUtils.isAvailablePasswordFormat(password3);
        });
    }
}