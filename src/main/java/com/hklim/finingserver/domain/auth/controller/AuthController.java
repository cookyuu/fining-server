package com.hklim.finingserver.domain.auth.controller;

import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;
import com.hklim.finingserver.domain.auth.service.AuthServiceNormal;
import com.hklim.finingserver.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceNormal authServiceNormal;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Long>> signupNormal(@RequestBody SignupRequestDto signupInfo) {
        return ResponseDto.created(authServiceNormal.signup(signupInfo));
    }
}
