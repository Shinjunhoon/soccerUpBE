package com.example.careercubebackend.config.security.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct; // Spring Boot 3.x+에서는 javax.annotation 대신 jakarta.annotation
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenValidityInMilliseconds;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * token Subject (사용자 ID 또는 Username) 조회
     *
     * @param token JWT
     * @return token Subject
     */
    public String getSubjectFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * token에서 특정 클레임 조회
     *
     * @param token JWT
     * @param claimsResolver 클레임을 추출할 Function
     * @param <T> 반환 타입
     * @return 클레임 값
     */
    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * token 사용자 모든 속성 정보 조회
     *
     * @param token JWT
     * @return All Claims
     */
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰 만료 일자 조회
     *
     * @param token JWT
     * @return 만료 일자
     */
    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Access Token 생성 (기본)
     *
     * @param subject 토큰 주체 (예: userId, username)
     * @return Access Token
     */
    public String generateAccessToken(final String subject) {
        return generateAccessToken(subject, new HashMap<>(), List.of());
    }

    /**
     * Access Token 생성 (추가 클레임 포함)
     *
     * @param subject 토큰 주체 (예: userId, username)
     * @param additionalClaims 추가할 클레임 (예: name)
     * @return Access Token
     */
    public String generateAccessToken(final String subject, final Map<String, Object> additionalClaims) {
        return generateAccessToken(subject, additionalClaims, List.of());
    }

    /**
     * ⭐ Access Token 생성 (추가 클레임 및 역할 목록 포함) ⭐
     *
     * @param subject 토큰 주체 (예: userId, username)
     * @param additionalClaims 추가할 클레임 (예: name)
     * @param roles 사용자의 역할(Role) 목록
     * @return Access Token
     */
    public String generateAccessToken(final String subject, final Map<String, Object> additionalClaims, final List<String> roles) {
        Map<String, Object> claims = new HashMap<>(additionalClaims);
        claims.put("roles", roles);

        return doGenerateToken(subject, claims, accessTokenValidityInMilliseconds);
    }

    /**
     * JWT 토큰 생성의 내부 구현
     *
     * @param subject 토큰 주체 (예: userId, username)
     * @param claims 토큰 클레임
     * @param expirationTimeMillis 토큰 만료 시간 (밀리초)
     * @return 생성된 JWT 토큰
     */
    private String doGenerateToken(final String subject, final Map<String, Object> claims, final long expirationTimeMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성
     *
     * @param subject 토큰 주체 (예: userId)
     * @return Refresh Token
     */
    public String generateRefreshToken(final String subject) {
        return doGenerateToken(subject, new HashMap<>(), refreshTokenValidityInMilliseconds);
    }

    /**
     * Refresh Token 생성 (long 타입 ID 편의 메서드)
     *
     * @param id 토큰 생성 id
     * @return Refresh Token
     */
    public String generateRefreshToken(final long id) {
        return doGenerateToken(String.valueOf(id), new HashMap<>(), refreshTokenValidityInMilliseconds);
    }

    /**
     * token 유효성 검증
     *
     * @param token JWT
     * @return token 검증 결과
     */
    public Boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * 토큰에서 userId 추출 (subject를 userId로 사용했다고 가정)
     *
     * @param token JWT
     * @return userId
     */
    public Long getUserIdFromToken(String token) {
        // getSubjectFromToken이 String 타입의 userId를 반환한다고 가정
        return Long.parseLong(getSubjectFromToken(token));
    }

    /**
     * 토큰에서 roles 추출
     *
     * @param token JWT
     * @return 역할(Role) 목록
     */
    public List<String> getRolesFromToken(String token) {
        // getClaimFromToken을 사용하여 "roles" 클레임 추출
        return getClaimFromToken(token, claims -> (List<String>) claims.get("roles"));
    }
}