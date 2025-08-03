package com.example.careercubebackend.api.JoinRequest.controller;

import com.example.careercubebackend.api.JoinRequest.application.impl.JoinRequestGetService;
import com.example.careercubebackend.api.JoinRequest.application.impl.JoinRequestService;
import com.example.careercubebackend.api.JoinRequest.dto.JoinRequestDto;
import com.example.careercubebackend.api.common.response.entity.ApiResponseEntity;
import com.example.careercubebackend.jwt.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class JoinRequestController {

    JoinRequestService joinRequestService;
    JoinRequestGetService joinRequestGetService;

    @PostMapping("/joinTeam")
    public ResponseEntity<ApiResponseEntity> joinTeam(@RequestBody JoinRequestDto joinRequestDto, Authentication authentication) {
        joinRequestService.teamJoinRequest(joinRequestDto, JwtUtil.getLoginId(authentication));
        return ApiResponseEntity.successResponseEntity();
    }

    @PostMapping("/searchRequester")
    public ResponseEntity<ApiResponseEntity> searchRequester(@RequestParam Long teamId) {
        return ApiResponseEntity.successResponseEntity( joinRequestGetService.getJoinRequestsForTeam(teamId));
    }
}
