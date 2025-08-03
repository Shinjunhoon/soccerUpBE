package com.example.careercubebackend.api.JoinRequest.application.impl;

import com.example.careercubebackend.api.JoinRequest.application.JoinRequestService;
import com.example.careercubebackend.api.JoinRequest.domain.entitiy.TeamJoinRequest;
import com.example.careercubebackend.api.JoinRequest.domain.repository.RepositoryRepository;
import com.example.careercubebackend.api.JoinRequest.dto.JoinRequestResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
public class JoinRequestGetService  {
    RepositoryRepository repositoryRepository;
    public List<JoinRequestResponseDto> getJoinRequestsForTeam(Long teamId) {
        List<TeamJoinRequest> requests = repositoryRepository.findByTeamId(teamId);

        log.info("Get join requests for team {}", requests);
        return requests.stream()
                .map(req -> new JoinRequestResponseDto(
                        req.getRequester().getUserInfo().getName(),
                        req.getPosition().toString(),
                        req.getRequester().getId(),
                        req.getProvince(),
                        req.getCityCode(),
                        req.getSkillLevel(),
                        req.getTeamIntro(),
                        req.getRequestStatus()
                ))
                .collect(Collectors.toList());
    }
}
