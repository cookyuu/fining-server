package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.JwtUserInfo;
import com.hklim.finingserver.domain.auth.dto.LoginRequestDto;
import com.hklim.finingserver.domain.auth.dto.LoginResponseDto;
import com.hklim.finingserver.domain.auth.dto.SignupRequestDto;
import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import com.hklim.finingserver.global.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceNormal implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    @Override
    public LoginResponseDto loginNormal(LoginRequestDto loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->
                new ApplicationErrorException(ApplicationErrorType.NO_SUCH_MEMBER_ERROR));
        if (member == null) {
            throw new UsernameNotFoundException("Email is not exist");
        }

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("Password is not matched");
        }
        JwtUserInfo userInfo = new JwtUserInfo();
        userInfo.toDto(member);

        String accessToken = jwtUtil.createAccessToken(userInfo);
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }

    private void chkSignupValidation(String email) {
        log.info("[SIGNUP PROCESS] Check Email Validation START ");
        if (memberRepository.existsByEmail(email)) {
            throw new ApplicationErrorException(ApplicationErrorType.DATA_DUPLICATED_ERROR,
                    "[SIGNUP PROCESS] Email is Duplicated, Please check again.");
        }
        log.info("[SIGNUP PROCESS] Check Email Validation END, SUCCESS! ");
    }
}
