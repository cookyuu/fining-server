package com.hklim.finingserver.domain.auth.dto;

import com.hklim.finingserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryEmailResponseDto {
    private String email;

    @Builder
    InquiryEmailResponseDto(String email) {
        this.email = email;
    }

    public void toDto(Member member) {
        this.email = member.getEmail();
    }

}
