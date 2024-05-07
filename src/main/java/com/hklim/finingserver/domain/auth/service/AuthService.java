package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.LoginRequestDto;
import com.hklim.finingserver.domain.auth.dto.LoginResponseDto;
import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;

public interface AuthService {
    Long signup(SignupRequestDto signupInfo);

    LoginResponseDto loginNormal(LoginRequestDto loginInfo);
}
