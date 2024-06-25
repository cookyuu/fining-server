package com.hklim.finingserver.domain.auth.controller;

import com.hklim.finingserver.domain.auth.dto.*;
import com.hklim.finingserver.domain.auth.service.AuthServiceNormal;
import com.hklim.finingserver.domain.auth.service.MailService;
import com.hklim.finingserver.global.dto.ResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceNormal authServiceNormal;
    private final MailService mailService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Long>> signupNormal(@RequestBody SignupRequestDto signupInfo) {
        return ResponseDto.created(authServiceNormal.signup(signupInfo));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<String>> logoutNormal(HttpServletRequest request, HttpServletResponse response) {
        authServiceNormal.logout(request.getHeader("Authorization"), response);
        return ResponseDto.ok("로그아웃이 정상처리 되었습니다.");
    }

    @PatchMapping("/reissue")
    public ResponseEntity<ResponseDto<AccessTokenResponseDto>> reissueAccessToken(HttpServletRequest request) {
        return ResponseDto.ok(authServiceNormal.reissueAccessToken(request));

    }

    @PostMapping("/email/auth-code")
    public ResponseEntity<ResponseDto<String>> sendEmailAuthCode(@RequestBody EmailAuthCodeRequestDto request) {
        mailService.sendEmail(request);
        return ResponseDto.ok("인증 번호 이메일 발송 완료");
    }

    @PostMapping("/email/authentication")
    public ResponseEntity<ResponseDto<String>> authenticateEmailAuthCode(@RequestBody EmailAuthenticationRequestDto request) {
        mailService.authenticateEmail(request);
        return ResponseDto.ok("이메일 인증 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<AccessTokenResponseDto>> loginNormal(@RequestBody LoginRequestDto loginInfo, HttpServletResponse response) {
        AccessTokenResponseDto res = authServiceNormal.login(loginInfo, response);
        return ResponseDto.ok(res);
    }

    @PostMapping("/inquiry/email")
    public ResponseEntity<ResponseDto<InquiryEmailResponseDto>> inquiryEmail(@RequestBody InquiryEmailRequestDto inquiryEmailInfo) {
        return ResponseDto.ok(authServiceNormal.inquiryEmail(inquiryEmailInfo));
    }

    @PostMapping("/inquiry/pw")
    public ResponseEntity<ResponseDto<InquiryPwResponseDto>> inquiryPw(@RequestBody InquiryPwRequestDto inquiryPwInfo) {
        return ResponseDto.ok(authServiceNormal.inquiryPw(inquiryPwInfo));
    }
}
