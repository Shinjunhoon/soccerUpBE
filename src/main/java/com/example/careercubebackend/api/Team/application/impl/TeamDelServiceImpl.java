package com.example.careercubebackend.api.Team.application.impl;


import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository;
import com.example.careercubebackend.api.TeamMember.exception.ApiException;
import com.example.careercubebackend.api.TeamMember.exception.ExceptionResult;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class TeamDelServiceImpl implements com.example.careercubebackend.api.Team.application.TeamDelServiceImpl {

    TeamRepository teamRepository;


    @Transactional
    @Override
    public void delTeam(Long teamId, Long userId) {


        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException(ExceptionResult.NOT_TEAM_OWNER)); // 팀을 찾을 수 없을 때 예외 발생


        if (team.getOwner() == null || !userId.equals(team.getOwner().getId())) {
            throw new ApiException(ExceptionResult.NOT_TEAM_OWNER); // 팀 소유자가 아닐 때 예외 발생
        }


        teamRepository.delete(team);
    }
}

