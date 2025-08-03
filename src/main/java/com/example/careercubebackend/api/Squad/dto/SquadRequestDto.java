package com.example.careercubebackend.api.Squad.dto;

import com.example.careercubebackend.api.SquadPosition.dto.SquadPositionRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class SquadRequestDto {

    private Long teamId;
    private String formationType;

    private List<SquadPositionRequestDto> positionRequestDtoList;
}
