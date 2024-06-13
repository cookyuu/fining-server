package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.LoginRequestDto;
import com.hklim.finingserver.domain.auth.dto.LoginResponseDto;
import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    Long signup(SignupRequestDto signupInfo);

    LoginResponseDto login(LoginRequestDto loginInfo, HttpServletResponse response);

    void logout(String accessToken, HttpServletResponse repsonse);
}
