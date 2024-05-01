package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;

public interface AuthService {
    public Long signup(SignupRequestDto signupInfo);
}
