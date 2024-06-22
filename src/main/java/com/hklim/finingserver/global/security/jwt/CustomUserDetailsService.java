package com.hklim.finingserver.global.security.jwt;

import com.hklim.finingserver.domain.auth.dto.JwtUserInfo;
import com.hklim.finingserver.domain.member.entity.Member;
import com.hklim.finingserver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("[VALIDATE-TOKEN] No such member exists"));
        JwtUserInfo userInfo = new JwtUserInfo();
        userInfo.toDto(member);

        return new CustomUserDetails(userInfo);
    }
}
