package com.hklim.finingserver.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailAuthenticationRequestDto {

    @Email
    @NotBlank(message = "이메일(필수)")
    private String email;

    @NotBlank(message = "인증코드(필수)")
    private String authCode;
}