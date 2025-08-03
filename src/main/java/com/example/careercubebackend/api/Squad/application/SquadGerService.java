package com.example.careercubebackend.api.Squad.application;

import com.example.careercubebackend.api.Squad.dto.SquadResponse;

import java.util.List;

public interface SquadGerService {

    public List<SquadResponse> getSquadByTeamId(Long teamId,Long userId);
}
