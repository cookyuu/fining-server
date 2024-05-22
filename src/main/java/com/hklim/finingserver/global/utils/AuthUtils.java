package com.hklim.finingserver.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class AuthUtils {
    public String createAuthCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(888888) + 111111);
    }

}
