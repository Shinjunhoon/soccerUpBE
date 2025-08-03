package com.example.careercubebackend.api.Team.application;

import com.example.careercubebackend.api.Team.dto.TeamResponseDto;
import com.example.careercubebackend.api.Team.dto.TeamResponseJoinUrl;

import java.util.List;

public interface TeamGetServiceImpl {

    public abstract List<TeamResponseJoinUrl> searchTeam(Long id);
    public abstract TeamResponseJoinUrl getTeam(String inviteCode);
    public List<TeamResponseDto> searchTeamByMember(Long userId);
}
