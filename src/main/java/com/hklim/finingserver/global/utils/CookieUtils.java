package com.hklim.finingserver.global.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
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

    public void removeCookie(String key, HttpServletResponse response) {
        log.info("[REMOVE-COOKIE-DATA] Remove cookie data, key : {}", key);
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
