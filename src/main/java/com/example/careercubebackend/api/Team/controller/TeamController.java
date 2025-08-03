package com.example.careercubebackend.api.Team.controller;


import com.example.careercubebackend.api.Team.application.impl.TeamAddServiceImpl;
import com.example.careercubebackend.api.Team.application.impl.TeamDelServiceImpl;
import com.example.careercubebackend.api.Team.application.impl.TeamGetServiceImpl;
import com.example.careercubebackend.api.Team.dto.TeamRequestDto;
import com.example.careercubebackend.api.TeamMember.dto.TeamMemberRequestDto;
import com.example.careercubebackend.api.common.response.entity.ApiResponseEntity;
import com.example.careercubebackend.jwt.JwtUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RestController
public class TeamController {

    TeamAddServiceImpl teamAddServiceImpl;
TeamGetServiceImpl teamGetServiceImpl;
TeamDelServiceImpl teamDelServiceImpl;

    @PostMapping("/creatTeam")
    public ResponseEntity<ApiResponseEntity> creatTeam(@RequestBody  TeamRequestDto teamRequestDto, Authentication authentication){
        return ApiResponseEntity.successResponseEntity(teamAddServiceImpl.createTeam(JwtUtil.getLoginId(authentication),teamRequestDto));
    }

    @GetMapping("/searchTeam")
    public ResponseEntity<ApiResponseEntity> searchTeam(Authentication authentication){
         return ApiResponseEntity.successResponseEntity(teamGetServiceImpl.searchTeam(JwtUtil.getLoginId(authentication)));
    }

    @GetMapping("/searchTeamByMember")
    public ResponseEntity<ApiResponseEntity> searchTeamByMember(Authentication authentication){
        return ApiResponseEntity.successResponseEntity(teamGetServiceImpl.searchTeamByMember(JwtUtil.getLoginId(authentication)));
    }

    @GetMapping("/invite/{uuid}")
    public ResponseEntity<ApiResponseEntity> findById(@PathVariable("uuid") String uuid){
        return ApiResponseEntity.successResponseEntity(teamGetServiceImpl.getTeam(uuid));
    }


    @DeleteMapping("teams/{teamId}")
    @PreAuthorize("hasRole('TEAM_OWNER')")
    public ResponseEntity<ApiResponseEntity> acceptedTeam(@PathVariable Long teamId, Authentication authentication) {
        teamDelServiceImpl.delTeam(teamId, JwtUtil.getLoginId(authentication));
        return ApiResponseEntity.successResponseEntity();
    }

}

