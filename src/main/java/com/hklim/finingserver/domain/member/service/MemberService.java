package com.hklim.finingserver.domain.member.service;

import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER));
        validateWithdrawnMember(member);
        return member;
    }

    private void validateWithdrawnMember(Member member) {
        if (member.isDeleted()) {
            throw new ApplicationErrorException(ApplicationErrorType.ALREADY_WITHDRAWN_MEMBER, "[FIND-MEMBER-DATA] This member is already Withdrawn");
        }
    }
}
