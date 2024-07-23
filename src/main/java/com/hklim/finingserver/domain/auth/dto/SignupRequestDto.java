package com.hklim.finingserver.domain.auth.dto;

import com.hklim.finingserver.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {
    // email 형식 판단 필요
    @NotBlank
    private String email;
    // 특수문자, 대소문자, 숫자 조합 판별식 필요

    @NotBlank
    private String password;
    // 글자수 제한 필요
    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}
