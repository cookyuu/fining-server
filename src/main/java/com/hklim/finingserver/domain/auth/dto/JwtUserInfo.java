package com.hklim.finingserver.domain.auth.dto;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class JwtUserInfo {
    private Long memberId;
    private String email;
    private String name;
    private String password;
    private RoleType role;

    public void toDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
