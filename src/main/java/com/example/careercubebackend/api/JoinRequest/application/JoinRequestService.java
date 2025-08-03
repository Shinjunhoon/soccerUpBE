package com.example.careercubebackend.api.JoinRequest.application;

import com.example.careercubebackend.api.JoinRequest.dto.JoinRequestDto;

public interface JoinRequestService {
    public void teamJoinRequest(JoinRequestDto joinRequestDto, Long requesterId);
}
