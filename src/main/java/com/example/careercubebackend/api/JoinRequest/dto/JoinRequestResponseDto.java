package com.example.careercubebackend.api.JoinRequest.dto;


import com.example.careercubebackend.api.JoinRequest.domain.enums.RequestStatus;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.SkillLevel;

public record JoinRequestResponseDto(
        String name,
        String position,
        Long id,
        Region province,
        String cityCode,
        SkillLevel skillLevel,
        String teamIntro,
        RequestStatus requestStatus) {}