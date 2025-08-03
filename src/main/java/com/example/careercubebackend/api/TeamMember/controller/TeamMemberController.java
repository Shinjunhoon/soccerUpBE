package com.example.careercubebackend.api.TeamMember.controller;

import com.example.careercubebackend.api.TeamMember.application.impl.TeamMemberAddService;
import com.example.careercubebackend.api.TeamMember.application.impl.TeamMemberGetService;
import com.example.careercubebackend.api.TeamMember.dto.TeamMemberRequestDto;
import com.example.careercubebackend.api.common.response.entity.ApiResponseEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RestController
public class TeamMemberController {
    TeamMemberAddService teamMemberAddService;
    TeamMemberGetService teamMemberGetService;


    @PostMapping("/acceptedTeam")
    @PreAuthorize("hasRole('TEAM_OWNER')")
    public ResponseEntity<ApiResponseEntity> acceptedTeam(@RequestBody TeamMemberRequestDto teamMemberRequestDto) {
        teamMemberAddService.addTeamMember(teamMemberRequestDto);

        return ApiResponseEntity.successResponseEntity();
    }



    @GetMapping("/teamMember/{teamId}")
        public ResponseEntity<ApiResponseEntity> getTeamMember(@PathVariable Long teamId) {
        return ApiResponseEntity.successResponseEntity(teamMemberGetService.FindByTeam_id(teamId));
    }
}
