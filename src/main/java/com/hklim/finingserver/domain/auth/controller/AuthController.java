package com.hklim.finingserver.domain.auth.controller;

import com.hklim.finingserver.domain.auth.dto.EmailAuthCodeRequestDto;
import com.hklim.finingserver.domain.auth.dto.EmailAuthenticationRequestDto;
import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;
import com.hklim.finingserver.domain.auth.service.AuthServiceNormal;
import com.hklim.finingserver.domain.auth.service.MailService;
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
    private final MailService mailService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Long>> signupNormal(@RequestBody SignupRequestDto signupInfo) {
        return ResponseDto.created(authServiceNormal.signup(signupInfo));
    }

    // 인증메일 발송
    @PostMapping("/email")
    public ResponseEntity<ResponseDto<String>> sendEmailAuthCode(@RequestBody EmailAuthCodeRequestDto request) {
        mailService.sendEmail(request);
        return ResponseDto.ok("인증 번호 이메일 발송 완료");
    }

    // 메일인증
    @PostMapping("/email/auth")
    public ResponseEntity<ResponseDto<String>> authenticateEmailAuthCode(@RequestBody EmailAuthenticationRequestDto request) {
        mailService.authenticateEmail(request);
        return ResponseDto.ok("이메일 인증 완료");
    }
}
