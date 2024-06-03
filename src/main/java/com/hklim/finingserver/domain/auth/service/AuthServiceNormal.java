package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.*;
import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import com.hklim.finingserver.global.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.regex.Pattern;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceNormal implements AuthService {

    @Value("${auth.pw.pattern}")
    String pwPattern;
    @Value("${auth.pw.temp.length}")
    int tempPwLength;

    private static final char[] randomAllCharacters = new char[]{
            //number
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            //uppercase
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            //lowercase
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            //special symbols
            '@', '$', '!', '%', '*', '?', '&'
    };

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public Long signup(SignupRequestDto signupInfo) {
        log.info("[SIGNUP PROCESS] START");
        chkSignupValidation(signupInfo.getEmail());

        SignupRequestDto saveInfo = SignupRequestDto.builder()
                .email(signupInfo.getEmail())
                .password(passwordEncoder.encode(signupInfo.getPassword()))
                .name(signupInfo.getName())
                .phoneNumber(signupInfo.getPhoneNumber())
                .build();
        Long res = memberRepository.save(saveInfo.toEntity()).getId();
        log.info("[SIGNUP PROCESS] END");
        return res;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->
                new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER));
        log.info("[NORMAL-LOGIN] Find Member email : {}", member.getEmail());

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("Password is not matched");
        }
        JwtUserInfo userInfo = new JwtUserInfo();
        userInfo.toDto(member);

        String accessToken = jwtUtils.createAccessToken(userInfo);
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

    public InquiryEmailResponseDto inquiryEmail(InquiryEmailRequestDto inquiryEmailInfo) {
        // 이름, 핸드폰번호로 조회
        String name = inquiryEmailInfo.getName();
        String phoneNumber = inquiryEmailInfo.getPhoneNumber();
        // 데이터 검증필요, 이름 형식, 핸드폰번호 형식
        Member member = memberRepository.findByNameAndPhoneNumber(name, phoneNumber).orElseThrow(
                () -> new ApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR));
        if (member == null) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER);
        } else {
            InquiryEmailResponseDto res = new InquiryEmailResponseDto();
            res.toDto(member);
            return res;
        }
    }

    public InquiryPwResponseDto inquiryPw(InquiryPwRequestDto inquiryPwInfo) {
        String email = inquiryPwInfo.getEmail();
        String name = inquiryPwInfo.getName();
        String phoneNumber = inquiryPwInfo.getPhoneNumber();
        log.info("{}, {}, {}", email, name, phoneNumber);
        Member member = memberRepository.findByEmailAndNameAndPhoneNumber(email, name, phoneNumber).orElseThrow(
                () -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_MEMBER));

        String tempPw = createTemporaryPw(tempPwLength);
        String encTempPw = passwordEncoder.encode(tempPw);
        member.updateTempPw(encTempPw);
        memberRepository.save(member);
        InquiryPwResponseDto res = new InquiryPwResponseDto(tempPw);
        return res;
    }

    private String createTemporaryPw(int tempPwLength) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        int randomAllCharactersLength = randomAllCharacters.length;
        for (int i=0; i<tempPwLength; i++) {
            sb.append(randomAllCharacters[random.nextInt(randomAllCharactersLength)]);
        }
        String tempPw = sb.toString();
        if (!Pattern.matches(pwPattern, tempPw)) {
            return createTemporaryPw(tempPwLength);
        }
        return tempPw;
    }
}
