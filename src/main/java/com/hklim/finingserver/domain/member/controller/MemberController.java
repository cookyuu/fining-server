package com.hklim.finingserver.domain.member.controller;

import com.hklim.finingserver.domain.auth.service.AuthServiceNormal;
import com.hklim.finingserver.domain.member.dto.UpdateMemberPwRequestDto;
import com.hklim.finingserver.domain.member.dto.UpdateMemberRequestDto;
import com.hklim.finingserver.domain.member.service.MemberService;
import com.hklim.finingserver.global.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final AuthServiceNormal authServiceNormal;
    private final MemberService memberService;
    @PostMapping("/withdrawal")
    public ResponseEntity<ResponseDto<String>> withdrawalMember(@AuthenticationPrincipal UserDetails user, HttpServletRequest request, HttpServletResponse response) {
        authServiceNormal.withdrawalMember(user, request, response);
        return ResponseDto.ok("회원 탈퇴 완료.");
    }

    @PutMapping("/info")
    public ResponseEntity<ResponseDto<String>> updateMember(@AuthenticationPrincipal UserDetails user, @RequestBody UpdateMemberRequestDto updateMemberInfo) {
        memberService.updateMember(user, updateMemberInfo);
        return ResponseDto.ok("회원정보 수정 완료");
    }

    @PutMapping("/info/pw")
    public ResponseEntity<ResponseDto<String>> updateMemberPw(@AuthenticationPrincipal UserDetails user, @RequestBody UpdateMemberPwRequestDto updateMemberPwInfo,
                                                              HttpServletRequest request, HttpServletResponse response) {
        memberService.updateMemberPw(user,updateMemberPwInfo);
        authServiceNormal.logout(request.getHeader("Authorization"), response);
        return ResponseDto.ok("비밀번호 변경 완료, 다시 변경된 비밀번호로 다시 로그인 해주세요. ");
    }
}
