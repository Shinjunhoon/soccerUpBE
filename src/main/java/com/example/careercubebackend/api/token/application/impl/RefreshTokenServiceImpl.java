package com.example.careercubebackend.api.token.application.impl;

import com.example.careercubebackend.api.token.application.RefreshTokenService;
import com.example.careercubebackend.api.token.dto.response.RefreshTokenResponseDTO;
import com.example.careercubebackend.api.token.exception.RefreshTokenException;
import com.example.careercubebackend.api.token.exception.RefreshTokenExceptionResult;
import com.example.careercubebackend.api.token.vo.RefreshToken;
import com.example.careercubebackend.config.security.provider.JwtProvider;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository; // TeamRepository 임포트
import com.example.careercubebackend.api.user.domain.repository.UserRepository; // UserRepository 임포트 (사용자 정보가 필요하다면)
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // ArrayList 임포트
import java.util.HashMap;
import java.util.List;    // List 임포트
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtProvider jwtProvider;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;


    @Override
    public RefreshTokenResponseDTO refreshToken(final String refreshToken) {

        checkRefreshToken(refreshToken);


        Long userId = Long.parseLong(jwtProvider.getSubjectFromToken(refreshToken));


        List<String> userRoles = getUserRolesFromDatabase(userId);


        Map<String, Object> additionalClaims = new HashMap<>();

        userRepository.findById(userId).ifPresent(user -> additionalClaims.put("name", user.getUserInfo().getName())); // User 엔티티의 username 필드 가정


        String newAccessToken = jwtProvider.generateAccessToken(String.valueOf(userId), additionalClaims, userRoles);


        RefreshToken.removeUserRefreshToken(userId);


        String newRefreshToken = jwtProvider.generateRefreshToken(userId);
        RefreshToken.putRefreshToken(newRefreshToken, userId);

        return RefreshTokenResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }


    private List<String> getUserRolesFromDatabase(Long userId) {
        List<String> roles = new ArrayList<>();
        roles.add("USER"); // 모든 사용자는 기본적으로 "USER" 역할을 가짐


        boolean isTeamOwner = teamRepository.existsByOwner_Id(userId);

        if (isTeamOwner) {
            roles.add("TEAM_OWNER");
        }
        // TODO: 필요한 다른 역할 (예: ADMIN)이 있다면 여기에 추가 로직 구현

        return roles;
    }


    private void checkRefreshToken(final String refreshToken) {
        if(Boolean.FALSE.equals(jwtProvider.validateToken(refreshToken))) {
            throw new RefreshTokenException(RefreshTokenExceptionResult.INVALID);
        }
    }
}