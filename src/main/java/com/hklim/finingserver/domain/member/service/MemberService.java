package com.hklim.finingserver.domain.member.service;

import com.hklim.finingserver.domain.member.dto.UpdateMemberPwRequestDto;
import com.hklim.finingserver.domain.member.dto.UpdateMemberRequestDto;
import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import com.hklim.finingserver.global.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthUtils authUtils;

    public Member findMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER));
        validateWithdrawnMember(member);
        return member;
    }

    private void validateWithdrawnMember(Member member) {
        if (member.isDeleted()) {
            throw new ApplicationErrorException(ApplicationErrorType.ALREADY_WITHDRAWN_MEMBER, "[FIND-MEMBER-DATA] Member is already Withdrawn");
        }
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public void updateMember(UserDetails user, UpdateMemberRequestDto updateMemberInfo) {
        Member member = findMemberById(Long.valueOf(user.getUsername()));
        member.updateInfo(updateMemberInfo.getName(),updateMemberInfo.getPhoneNumber());
        saveMember(member);
    }

    public void updateMemberPw(UserDetails user, UpdateMemberPwRequestDto updateMemberPwInfo) {
        Member member = findMemberById(Long.valueOf(user.getUsername()));
        authUtils.checkPassword(member.getPassword(), updateMemberPwInfo.getPassword());
        checkEqualNewPw(updateMemberPwInfo.getNewPassword(), updateMemberPwInfo.getNewPasswordChk());
        member.updatePw(authUtils.encPassword(updateMemberPwInfo.getNewPassword()));
        log.info("[UPDATE-PW] Update new password. member email : {}", member.getEmail());
        memberRepository.save(member);
    }

    private void checkEqualNewPw(String newPw, String newPwChk) {
        log.info("[CHECK-NEW-PW] Check new password is matches. ");
        if (!newPw.equals(newPwChk)) {
            throw new ApplicationErrorException(ApplicationErrorType.DATA_MATCHING_FAIL, "[CHECK-NEW-PW] New password is unmatched. try again!");
        }
    }
}
