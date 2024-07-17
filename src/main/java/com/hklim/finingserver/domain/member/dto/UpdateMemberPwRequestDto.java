package com.hklim.finingserver.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMemberPwRequestDto {
    private String password;
    private String newPassword;
    private String newPasswordChk;
}
