package com.example.careercubebackend.api.Team.application;

import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.dto.TeamRequestDto;
import com.example.careercubebackend.api.Team.dto.TeamResponseDto;
import com.example.careercubebackend.api.Team.dto.TeamResponseJoinUrl;

import java.util.List;

public interface TeamAddServiceImpl {

    public abstract TeamResponseJoinUrl createTeam(Long id, TeamRequestDto teamRequestDto);



}
