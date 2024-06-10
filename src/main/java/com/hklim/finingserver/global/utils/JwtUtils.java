package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.domain.auth.dto.JwtUserInfo;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;

    public JwtUtils(@Value("${auth.jwt.secret_key}") String secretKey,
                    @Value("${auth.jwt.access_expiration_time}") String accessTokenExpTime,
                    @Value("${auth.jwt.refresh_expiration_time}") String refreshTokenExpTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = Long.parseLong(accessTokenExpTime);
        this.refreshTokenExpTime = Long.parseLong(refreshTokenExpTime);
    }

    public String createAccessToken(JwtUserInfo member) {
        return createToken(member, accessTokenExpTime);
    }
    public String createRefreshToken(JwtUserInfo member) {
        return createToken(member, refreshTokenExpTime);
    }

    /*
        JWT 생성
    */
    private String createToken(JwtUserInfo member, long expiredTime) {
        log.info("Claims member id : " + member.getMemberId());
        log.info("Claims email : " + member.getEmail());
        log.info("Claims role : " + member.getRole());

        Claims claims = Jwts.claims();
        claims.put("memberId", member.getMemberId());
        claims.put("email", member.getEmail());
        claims.put("role", member.getRole());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expiredTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    /*
    * Token에서 MemberId 추출
    */
    public Long getUserId(String token) {
        return parseClaims(token).get("memberId", Long.class);
    }

    /*
    * JWT Claims 추출
    */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public  boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("[VALIDATE JWT TOKEN] Invalid JWT Token. ", e);
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_JWT_VALIDATION, "유효하지 않은 토큰입니다. ");
        } catch (ExpiredJwtException e) {
            log.info("[VALIDATE JWT TOKEN] Expired JWT Token. ", e);
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_JWT_VALIDATION, "토큰 유효기한이 만료되었습니다. ");
        } catch (UnsupportedJwtException e) {
            log.info("[VALIDATE JWT TOKEN] Unsupported JWT Token. ", e);
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_JWT_VALIDATION, "지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("[VALIDATE JWT TOKEN] JWT Claims String is empty. ", e);
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_JWT_VALIDATION, "토큰 데이터가 비어있습니다.");
        }
    }

}
