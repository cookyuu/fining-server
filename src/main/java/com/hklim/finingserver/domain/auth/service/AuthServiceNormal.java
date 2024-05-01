package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceNormal implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long signup(SignupRequestDto signupInfo) {
        log.info("[SIGNUP PROCESS] START");
        chkSignupValidation(signupInfo.getEmail());

        SignupRequestDto saveInfo = SignupRequestDto.builder()
                .email(signupInfo.getEmail())
                .password(passwordEncoder.encode(signupInfo.getPassword()))
                .name(signupInfo.getName())
                .build();
        Long res = memberRepository.save(saveInfo.toEntity()).getId();
        log.info("[SIGNUP PROCESS] END");
        return res;
    }

    private void chkSignupValidation(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new ApplicationErrorException(ApplicationErrorType.DATA_DUPLICATED_ERROR,
                    "Email Data is Duplicated, Please check again.");
        }
        log.info("[SIGNUP VALIDATION CHECK PROCESS] SUCCESS!! ");
    }
}
