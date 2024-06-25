package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.LoginRequestDto;
import com.hklim.finingserver.domain.auth.dto.AccessTokenResponseDto;
import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    Long signup(SignupRequestDto signupInfo);

    AccessTokenResponseDto login(LoginRequestDto loginInfo, HttpServletResponse response);

    void logout(String accessToken, HttpServletResponse response);

    AccessTokenResponseDto reissueAccessToken(HttpServletRequest request);
}
