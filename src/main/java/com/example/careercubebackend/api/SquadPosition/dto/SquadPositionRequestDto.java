package com.example.careercubebackend.api.SquadPosition.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SquadPositionRequestDto {
    private String fieldPositionCode;
    private Long teamMemberId;
}
