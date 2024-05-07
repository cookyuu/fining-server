package com.hklim.finingserver.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hklim.finingserver.global.dto.ErrorResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j(topic = "UnAUTHORIZATION_EXCEPTION_HANDLER")
@AllArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Not Authenticated Request", authException);

        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.name(), authException.getMessage());

        String resBody = objectMapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resBody);
    }
}
