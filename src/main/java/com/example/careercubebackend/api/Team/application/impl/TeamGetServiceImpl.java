package com.example.careercubebackend.api.Team.application.impl;

import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository;
import com.example.careercubebackend.api.Team.dto.TeamResponseDto;
import com.example.careercubebackend.api.Team.dto.TeamResponseJoinUrl;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.TeamMember.domain.repository.TeamMemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TeamGetServiceImpl implements com.example.careercubebackend.api.Team.application.TeamGetServiceImpl {
    TeamRepository teamRepository;

    TeamMemberRepository teamMemberRepository;


    @Override
    public List<TeamResponseJoinUrl> searchTeam(Long id) {
        List<Team> teams = teamRepository.findByOwner_Id(id);

        return teams.stream()
                .map(team -> new TeamResponseJoinUrl(team))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamResponseDto> searchTeamByMember(Long userId) {
        // 변경된 메서드 호출
        List<TeamMember> teamMembers = teamMemberRepository.findByUserWithTeam(userId);

        return teamMembers.stream()
                .map(tm -> new TeamResponseDto(tm.getTeam())) // tm.getTeam() 호출 시 추가 쿼리 발생하지 않음
                .collect(Collectors.toList());
    }
    @Override
    public TeamResponseJoinUrl getTeam(String inviteCode) {
        Team team = teamRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("초대 코드에 해당하는 팀이 없습니다."));
        return new TeamResponseJoinUrl(team);
    }
}
