package com.example.careercubebackend.api.Squad.dto;


import com.example.careercubebackend.api.SquadPosition.dto.SquadPositionResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SquadResponse {

    private Long id;

    private Long teamId;

    private String formationType;

    private Boolean isDefault;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<SquadPositionResponse> positions;
}