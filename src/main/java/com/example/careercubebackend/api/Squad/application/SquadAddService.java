package com.example.careercubebackend.api.Squad.application;

import com.example.careercubebackend.api.Squad.domain.entity.Squad;
import com.example.careercubebackend.api.Squad.dto.SquadRequestDto;
import com.example.careercubebackend.api.Squad.dto.SquadResponse;


public interface SquadAddService {

    public SquadResponse addSquad(SquadRequestDto squadRequestDto, Long userId);
    public SquadResponse updateSquad(Long squadId, SquadRequestDto request, Long userId);
}
