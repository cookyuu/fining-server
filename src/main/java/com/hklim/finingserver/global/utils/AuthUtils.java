package com.hklim.finingserver.global.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final PasswordEncoder passwordEncoder;
    public String createAuthCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(888888) + 111111);
    }

    public void checkPassword(String memberPw, String reqPw) {
        if (!passwordEncoder.matches(reqPw, memberPw)) {
            throw new BadCredentialsException("Password is not matched");
        }
    }

    public String encPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
