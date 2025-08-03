package com.example.careercubebackend.api.TeamMember.application.impl;

import com.example.careercubebackend.api.JoinRequest.domain.entitiy.TeamJoinRequest;
import com.example.careercubebackend.api.JoinRequest.domain.enums.RequestStatus;
import com.example.careercubebackend.api.JoinRequest.domain.repository.RepositoryRepository;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.TeamMember.domain.repository.TeamMemberRepository;
import com.example.careercubebackend.api.TeamMember.dto.TeamMemberRequestDto;
import com.example.careercubebackend.api.TeamMember.exception.ApiException;
import com.example.careercubebackend.api.TeamMember.exception.ExceptionResult;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Service
public class TeamMemberAddService implements com.example.careercubebackend.api.TeamMember.application.TeamMemberAddService {

    RepositoryRepository repositoryRepository;
    TeamMemberRepository teamMemberRepository;
    TeamRepository teamRepository;

    @Override
    @Transactional
    public void addTeamMember(TeamMemberRequestDto teamMemberRequestDto) {

        Long currentUserId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        TeamJoinRequest request = repositoryRepository.findByRequesterIdAndTeamId(teamMemberRequestDto.getUserId(), teamMemberRequestDto.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("해당 팀과 요청에 대한 정보가 일치하지 않습니다."));


        Team team = request.getTeam();


        if (!currentUserId.equals(team.getOwner().getId())) {
                  throw new ApiException(ExceptionResult.NOT_TEAM_OWNER);
        }


        request.setRequestStatus(RequestStatus.APPROVED);


        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(request.getTeam());
        teamMember.setUser(request.getRequester());
        teamMember.setPosition(request.getPosition());

        teamMemberRepository.save(teamMember);


        repositoryRepository.delete(request);
    }
}