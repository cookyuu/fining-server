package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.domain.auth.dto.JwtUserInfo;
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
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;

    public JwtUtil (@Value("${jwt.secret_key}") String secretKey,
                    @Value("${jwt.access_expiration_time}") String accessTokenExpTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = Long.parseLong(accessTokenExpTime);
    }

    /*
    * Access Token 생성
    */

    public String createAccessToken(JwtUserInfo member) {
        return createToken(member, accessTokenExpTime);
    }

    /*
    * JWT 생성
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
        } catch (ExpiredJwtException e) {
            log.info("[VALIDATE JWT TOKEN] Expired JWT Token. ", e);
        } catch (UnsupportedJwtException e) {
            log.info("[VALIDATE JWT TOKEN] Unsupported JWT Token. ", e);
        } catch (IllegalArgumentException e) {
            log.info("[VALIDATE JWT TOKEN] JWT Claims String is empty. ", e);
        }
        return false;
    }

}
