package com.example.careercubebackend.api.TeamMember.application;

import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.TeamMember.dto.TeamMemberResponseDto;

import java.util.List;

public interface TeamMemberGetService {
    public List<TeamMemberResponseDto> FindByTeam_id(Long team_id);
}
