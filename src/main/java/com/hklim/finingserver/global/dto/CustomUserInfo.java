package com.hklim.finingserver.global.dto;

import com.hklim.finingserver.global.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomUserInfo{
    private Long memberId;
    private String email;
    private String name;
    private String password;
    private RoleType role;
}
