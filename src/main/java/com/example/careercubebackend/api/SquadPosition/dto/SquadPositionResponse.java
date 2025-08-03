package com.example.careercubebackend.api.SquadPosition.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SquadPositionResponse { // static 키워드를 제거합니다.

    private Long id;

    private String fieldPositionCode;

    private Long teamMemberId;

    private String playerName;



}