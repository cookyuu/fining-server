package com.hklim.finingserver.global.utils;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {
    public String getValue(Cookie[] cookies, String key) {
        String value = "";
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if (cookieName.equals(key)) {
                value = cookie.getValue();
            }
        }
        return value;
    }
}
