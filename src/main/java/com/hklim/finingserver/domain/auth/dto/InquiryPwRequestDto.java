package com.hklim.finingserver.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryPwResponseDto {
    private String email;
    private String name;
    private String phoneNumer;
}
