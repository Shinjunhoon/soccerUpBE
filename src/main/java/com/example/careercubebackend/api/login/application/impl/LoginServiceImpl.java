package com.example.careercubebackend.api.login.application.impl;

import com.example.careercubebackend.api.login.application.LoginService;
import com.example.careercubebackend.api.login.dto.request.LoginRequestDTO;
import com.example.careercubebackend.api.login.dto.response.LoginResponseDTO;
import com.example.careercubebackend.api.login.exception.LoginException;
import com.example.careercubebackend.api.login.exception.LoginExceptionResult;
import com.example.careercubebackend.api.token.vo.RefreshToken;
import com.example.careercubebackend.api.user.application.UserGetService;
import com.example.careercubebackend.api.user.dto.response.UserGetResponseDTO;
import com.example.careercubebackend.config.security.provider.JwtProvider;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository; // <-- Import TeamRepository
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserGetService userGetService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public LoginResponseDTO login(final LoginRequestDTO loginRequestDTO) {

        UserGetResponseDTO userInfo = userGetService.getUserByUserId(loginRequestDTO.getUserId());


        if (!bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), userInfo.password())) {
            throw new LoginException(LoginExceptionResult.NOT_CORRECT);
        }


        List<String> userRoles = getUserRolesFromDatabase(userInfo.id());


        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userInfo.name());


        String accessToken = jwtProvider.generateAccessToken(String.valueOf(userInfo.id()), claims, userRoles);


        RefreshToken.removeUserRefreshToken(userInfo.id());


        String refreshToken = jwtProvider.generateRefreshToken(userInfo.id());
        RefreshToken.putRefreshToken(refreshToken, userInfo.id());

        return LoginResponseDTO.builder()
                .userName(userInfo.name())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    private List<String> getUserRolesFromDatabase(Long userId) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER"); // All users typically have a base 'USER' role


        boolean isTeamOwner = teamRepository.existsByOwner_Id(userId);

        if (isTeamOwner) {
            roles.add("ROLE_TEAM_OWNER");
        }
        // TODO: Add logic here to fetch any other roles (e.g., "ADMIN")

        return roles;
    }
}