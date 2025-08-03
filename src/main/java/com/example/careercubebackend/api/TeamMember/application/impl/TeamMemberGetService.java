package com.example.careercubebackend.api.TeamMember.application.impl;


import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.TeamMember.domain.repository.TeamMemberRepository;
import com.example.careercubebackend.api.TeamMember.dto.TeamMemberResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class TeamMemberGetService implements com.example.careercubebackend.api.TeamMember.application.TeamMemberGetService {
    TeamMemberRepository teamMemberRepository;


    @Override
    public List<TeamMemberResponseDto> FindByTeam_id(Long team_id) {
        List<TeamMember> teamMembers = teamMemberRepository.findByTeam_Id(team_id);

        return teamMembers.stream()
                .map(ts -> new TeamMemberResponseDto(ts.getId(),ts.getUser().getUserInfo().getName(),ts.getPosition()))
                .collect(Collectors.toList());
    }
}