package com.example.careercubebackend.config.security.filter;

import com.example.careercubebackend.api.user.application.UserGetService;
import com.example.careercubebackend.config.security.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserGetService userGetService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        Long userId = null;
        List<String> roles = null;

        log.info("Authorization Header: {}", authorizationHeader);

        // "Bearer " 접두사로 시작하는 JWT 토큰 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7); // "Bearer " 제거

            try {
                // 토큰 유효성 검증
                if (jwtProvider.validateToken(jwtToken)) {
                    userId = jwtProvider.getUserIdFromToken(jwtToken); // 토큰에서 userId 추출
                    roles = jwtProvider.getRolesFromToken(jwtToken);   // 토큰에서 roles 추출
                }
            } catch (Exception e) {

                log.warn("JWT token processing error: {}", e.getMessage());

                filterChain.doFilter(request, response);
                return;
            }
        }


        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            SecurityContextHolder.getContext().setAuthentication(createAuthentication(userId, roles));
        }

        filterChain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken createAuthentication(Long userId, List<String> roles) {
        // roles 문자열 목록을 SimpleGrantedAuthority 객체 컬렉션으로 변환
        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        return new UsernamePasswordAuthenticationToken(
                userId,
                null,
                authorities
        );
    }
}