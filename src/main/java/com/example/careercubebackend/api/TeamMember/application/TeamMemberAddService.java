package com.example.careercubebackend.api.TeamMember.application;

import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.TeamMember.dto.TeamMemberRequestDto;

public interface TeamMemberAddService {

    void addTeamMember(TeamMemberRequestDto teamMemberRequestDto);
}
