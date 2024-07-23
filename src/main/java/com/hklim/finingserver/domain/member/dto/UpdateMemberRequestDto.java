package com.hklim.finingserver.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMemberRequestDto {
    private String name;
    private String phoneNumber;
}
