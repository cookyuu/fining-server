package com.hklim.finingserver.domain.ui.dto;

import com.hklim.finingserver.domain.member.entity.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileUiDataResponseDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
